package me.arcanecici.irc.client.ui.menu;

import lombok.Getter;
import me.arcanecici.irc.client.ui.AbstractMenu;
import me.arcanecici.irc.client.ui.menu.impl.GuiNewChat;
import me.arcanecici.irc.client.ui.menu.impl.IRCChatMenu;

import java.util.HashMap;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Getter
public class MenuManager {
    public final IRCChatMenu lobby;
    public final GuiNewChat guiNewChat;
    public HashMap<String, AbstractMenu> menus = new HashMap<>();

    public MenuManager() {
        this.menus.put("lobby", this.lobby = new IRCChatMenu());
        this.menus.put("chatgui", this.guiNewChat = new GuiNewChat());
    }

}
