package RewardedIvan.asteroidclient.gui.themes.asteroid;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.WWidget;
import RewardedIvan.asteroidclient.gui.utils.BaseWidget;
import RewardedIvan.asteroidclient.utils.render.color.Color;

public interface AsteroidWidget extends BaseWidget {
    default AsteroidGuiTheme theme() {
        return (AsteroidGuiTheme) getTheme();
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, boolean pressed, boolean mouseOver) {
        AsteroidGuiTheme theme = theme();
        double s = theme.scale(2);

        renderer.quad(widget.x + s, widget.y + s, widget.width - s * 2, widget.height - s * 2, theme.backgroundColor.get(pressed, mouseOver));

        Color outlineColor = theme.outlineColor.get(pressed, mouseOver);
        renderer.quad(widget.x, widget.y, widget.width, s, outlineColor);
        renderer.quad(widget.x, widget.y + widget.height - s, widget.width, s, outlineColor);
        renderer.quad(widget.x, widget.y + s, s, widget.height - s * 2, outlineColor);
        renderer.quad(widget.x + widget.width - s, widget.y + s, s, widget.height - s * 2, outlineColor);
    }
}
