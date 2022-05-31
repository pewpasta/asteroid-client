package RewardedIvan.asteroidclient.systems.modules.misc;

import RewardedIvan.asteroidclient.events.game.SendMessageEvent;
import RewardedIvan.asteroidclient.mixin.ChatHudAccessor;
import RewardedIvan.asteroidclient.settings.*;
import it.unimi.dsi.fastutil.chars.Char2CharArrayMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import RewardedIvan.asteroidclient.events.game.ReceiveMessageEvent;
import RewardedIvan.asteroidclient.systems.modules.Categories;
import RewardedIvan.asteroidclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BetterChat extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgFilter = settings.createGroup("Filter");
    private final SettingGroup sgLongerChat = settings.createGroup("Longer Chat");
    private final SettingGroup sgPrefix = settings.createGroup("Prefix");
    private final SettingGroup sgSuffix = settings.createGroup("Suffix");

    private final Setting<Boolean> timestamps = sgGeneral.add(new BoolSetting.Builder()
        .name("timestamps")
        .description("Adds client side time stamps to the beginning of chat messages.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> playerHeads = sgGeneral.add(new BoolSetting.Builder()
        .name("player-heads")
        .description("Displays player heads next to their messages.")
        .defaultValue(true)
        .build()
    );

    // Filter

    private final Setting<Boolean> antiSpam = sgFilter.add(new BoolSetting.Builder()
        .name("anti-spam")
        .description("Blocks duplicate messages from filling your chat.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> antiSpamDepth = sgFilter.add(new IntSetting.Builder()
        .name("depth")
        .description("How many messages to filter.")
        .defaultValue(20)
        .min(1)
        .sliderMin(1)
        .visible(antiSpam::get)
        .build()
    );

    private final Setting<Boolean> filterRegex = sgFilter.add(new BoolSetting.Builder()
        .name("filter-regex")
        .description("Filter out chat messages that match the regex filter.")
        .defaultValue(false)
        .build()
    );

    private final Setting<List<String>> regexFilters = sgFilter.add(new StringListSetting.Builder()
        .name("regex-filter")
        .description("Regex filter used for filtering chat messages.")
        .visible(filterRegex::get)
        .build()
    );


    // Longer chat

    private final Setting<Boolean> infiniteChatBox = sgLongerChat.add(new BoolSetting.Builder()
        .name("infinite-chat-box")
        .description("Lets you type infinitely long messages.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> longerChatHistory = sgLongerChat.add(new BoolSetting.Builder()
        .name("longer-chat-history")
        .description("Extends chat length.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> longerChatLines = sgLongerChat.add(new IntSetting.Builder()
        .name("extra-lines")
        .description("The amount of extra chat lines.")
        .defaultValue(1000)
        .min(100)
        .sliderRange(100, 1000)
        .visible(longerChatHistory::get)
        .build()
    );

    // Prefix

    private final Setting<Boolean> prefix = sgPrefix.add(new BoolSetting.Builder()
        .name("prefix")
        .description("Adds a prefix to your chat messages.")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> prefixText = sgPrefix.add(new StringSetting.Builder()
        .name("text")
        .description("The text to add as your prefix.")
        .defaultValue("> ")
        .visible(prefix::get)
        .build()
    );

    private final Setting<Boolean> prefixSmallCaps = sgPrefix.add(new BoolSetting.Builder()
        .name("small-caps")
        .description("Uses small caps in the prefix.")
        .visible(prefix::get)
        .defaultValue(false)
        .build()
    );

    // Suffix

    private final Setting<Boolean> suffix = sgSuffix.add(new BoolSetting.Builder()
        .name("suffix")
        .description("Adds a suffix to your chat messages.")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> suffixText = sgSuffix.add(new StringSetting.Builder()
        .name("text")
        .description("The text to add as your suffix.")
        .defaultValue("| asteroid btw")
        .visible(suffix::get)
        .build()
    );

    private final Setting<Boolean> suffixSmallCaps = sgSuffix.add(new BoolSetting.Builder()
        .name("small-caps")
        .description("Uses small caps in the suffix.")
        .defaultValue(true)
        .visible(suffix::get)
        .build()
    );

    private final Char2CharMap SMALL_CAPS = new Char2CharArrayMap();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public BetterChat() {
        super(Categories.Misc, "better-chat", "Improves your chat experience in various ways.");
    }

    @EventHandler
    private void onMessageReceive(ReceiveMessageEvent event) {
        ((ChatHudAccessor) mc.inGameHud.getChatHud()).getVisibleMessages().removeIf((message) -> message.getId() == event.id && event.id != 0);
        ((ChatHudAccessor) mc.inGameHud.getChatHud()).getMessages().removeIf((message) -> message.getId() == event.id && event.id != 0);

        Text message = event.getMessage();

        if (filterRegex.get()) {
            for (int i = 0; i < regexFilters.get().size(); i++) {
                Pattern p;

                try {
                    p = Pattern.compile(regexFilters.get().get(i));
                }
                catch (PatternSyntaxException e) {
                    String removed = regexFilters.get().remove(i);
                    error("Removing Invalid regex: %s", removed);
                    continue;
                }

                if (p.matcher(message.getString()).find()) {
                    event.cancel();
                    return;
                }
            }
        }

        if (timestamps.get()) {
            Matcher matcher = Pattern.compile("^(<[0-9]{2}:[0-9]{2}>\\s)").matcher(message.getString());
            if (matcher.matches()) message.getSiblings().subList(0, 8).clear();

            Text timestamp = new LiteralText("<" + dateFormat.format(new Date()) + "> ").formatted(Formatting.GRAY);

            message = new LiteralText("").append(timestamp).append(message);
        }

        if (playerHeads.get()) {
            message = new LiteralText("  ").append(message);
        }

        for (int i = 0; i < antiSpamDepth.get(); i++) {
            if (antiSpam.get()) {
                Text antiSpammed = appendAntiSpam(message, i);

                if (antiSpammed != null) {
                    message = antiSpammed;
                    ((ChatHudAccessor) mc.inGameHud.getChatHud()).getMessages().remove(i);
                    ((ChatHudAccessor) mc.inGameHud.getChatHud()).getVisibleMessages().remove(i);
                }
            }
        }

        event.setMessage(message);
    }
    @EventHandler
    private void onMessageSend(SendMessageEvent event) {
        String message = event.message;

        message = getPrefix() + message + getSuffix();

        event.message = message;
    }

    private Text appendAntiSpam(Text text, int index) {
        List<ChatHudLine<OrderedText>> visibleMessages = ((ChatHudAccessor) mc.inGameHud.getChatHud()).getVisibleMessages();
        if (visibleMessages.isEmpty() || index < 0 || index > visibleMessages.size() - 1) return null;

        ChatHudLine<OrderedText> visibleMessage = visibleMessages.get(index);

        LiteralText parsed = new LiteralText("");

        visibleMessage.getText().accept((i, style, codePoint) -> {
            parsed.append(new LiteralText(new String(Character.toChars(codePoint))).setStyle(style));
            return true;
        });

        String oldMessage = parsed.getString();
        String newMessage = text.getString();

        if (oldMessage.equals(newMessage)) {
            return parsed.append(new LiteralText(" (2)").formatted(Formatting.GRAY));
        }
        else {
            Matcher matcher = Pattern.compile(".*(\\([0-9]+\\)$)").matcher(oldMessage);

            if (!matcher.matches()) return null;

            String group = matcher.group(matcher.groupCount());
            int number = Integer.parseInt(group.substring(1, group.length() - 1));

            String counter = " (" + number + ")";

            if (oldMessage.substring(0, oldMessage.length() - counter.length()).equals(newMessage)) {
                for (int i = 0; i < counter.length(); i++) parsed.getSiblings().remove(parsed.getSiblings().size() - 1);
                return parsed.append(new LiteralText( " (" + (number + 1) + ")").formatted(Formatting.GRAY));
            }
        }

        return null;
    }

    // Prefix and Suffix

    private String getPrefix() {
        return prefix.get() ? prefixText.get() : "";
    }

    private String getSuffix() {
        return suffix.get() ? suffixText.get() : "";
    }

    // Longer chat

    public boolean isInfiniteChatBox() {
        return isActive() && infiniteChatBox.get();
    }

    public boolean isLongerChat() {
        return isActive() && longerChatHistory.get();
    }

    public boolean displayPlayerHeads() { return isActive() && playerHeads.get(); }

    public int getChatLength() {
        return longerChatLines.get();
    }
}
