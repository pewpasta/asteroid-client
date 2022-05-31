/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.commands.commands;

import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.utils.player.ChatUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import RewardedIvan.asteroidclient.systems.commands.Command;
import RewardedIvan.asteroidclient.systems.commands.Commands;
import RewardedIvan.asteroidclient.systems.config.Config;
import net.minecraft.command.CommandSource;
import net.minecraft.text.BaseText;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CommandsCommand extends Command {
    public CommandsCommand() {
        super("commands", "List of all commands.", "help");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            ChatUtils.info("--- Commands ((highlight)%d(default)) ---", Commands.get().getCount());

            BaseText commands = new LiteralText("");
            Commands.get().getAll().forEach(command -> commands.append(getCommandText(command)));
            ChatUtils.sendMsg(commands);

            return SINGLE_SUCCESS;
        });
    }

    private BaseText getCommandText(Command command) {
        // Hover tooltip
        BaseText tooltip = new LiteralText("");

        tooltip.append(new LiteralText(Utils.nameToTitle(command.getName())).formatted(Formatting.BLUE, Formatting.BOLD)).append("\n");

        BaseText aliases = new LiteralText(Config.get().prefix.get() + command.getName());
        if (command.getAliases().size() > 0) {
            aliases.append(", ");
            for (String alias : command.getAliases()) {
                if (alias.isEmpty()) continue;
                aliases.append(Config.get().prefix.get() + alias);
                if (!alias.equals(command.getAliases().get(command.getAliases().size() - 1))) aliases.append(", ");
            }
        }
        tooltip.append(aliases.formatted(Formatting.GRAY)).append("\n\n");

        tooltip.append(new LiteralText(command.getDescription()).formatted(Formatting.WHITE));

        // Text
        BaseText text = new LiteralText(Utils.nameToTitle(command.getName()));
        if (command != Commands.get().getAll().get(Commands.get().getAll().size() - 1)) text.append(new LiteralText(", ").formatted(Formatting.GRAY));
        text.setStyle(text
                .getStyle()
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))
                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Config.get().prefix.get() + command.getName()))
        );

        return text;
    }

}
