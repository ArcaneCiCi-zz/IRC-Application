package me.arcanecici.irc.client;

import lombok.Getter;
import lombok.Setter;
import me.arcanecici.irc.client.feature.FeatureManager;
import me.arcanecici.irc.client.ui.AbstractMenu;
import me.arcanecici.irc.client.ui.menu.MenuManager;
import me.arcanecici.irc.client.util.DataUtil;
import me.arcanecici.irc.client.websocket.WSClientOverride;

import java.net.URI;
import java.util.HashMap;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

@Getter
@Setter
public class Client {
    // Your username, launch the JAR with the arguments "--username <username>"
    public static String alias;

    // Asset server URL, launch the JAR with the arguments "--asseturl <url>"
    public static String assetUrl;

    @Getter
    private static Client instance;

    private FeatureManager featureManager;
    private MenuManager menuManager;
    private WSClientOverride websocket;
    private AbstractMenu currentScreen;

    public Client() {
        instance = this;
        this.init();
    }

    public static void main(String[] args) {
        alias = args[1];
        assetUrl = args[2];
        new Client();
    }

    // Setup...
    public void init() {
        this.featureManager = new FeatureManager();
        this.menuManager = new MenuManager();

        this.attemptWSConnect();
    }

    // Connects to the websocket server with a Base64 string & the username you gave when running it.
    public void attemptWSConnect() {
        final HashMap<String, String> headers = new HashMap<>();
        headers.put("clientKey",
                DataUtil.encodeAndDecodeKey(
                        "7ca36efd524e6c34c26cbd628b19aa835" +
                                "aceb94ea7f2ca7f33d1b8f51476bc597d" +
                                "4bf9ad5111d8f39ef5351b3b090bce47f" +
                                "023002fe69928e79f6f8147f6fe051f2f" +
                                "159041f932f5190308d7441fc3cecead0" +
                                "851662d3217485827e640a4183fa5bc8c" +
                                "ef5ff7d1473d2746a37fbc8b94318ff0d" +
                                "3aeb467017c0ea5cb33b3e6967453986e" +
                                "1450b35ad47861f679cf7db5a6c170bcf" +
                                "b67544983ec1e36b27ee8c5721da39d27" +
                                "dbfa0cdc15ba3cbaa425e8a8b96b81ab6" +
                                "65f3ebc41563a0e9270695d3d68887cfa" +
                                "b2c07b290718307f764afba684b17fcfd" +
                                "71323f64206e5fa378b4ee89e80885733" +
                                "080065dd34a5c838898906b8d43de9f1d"
                )
        );
        headers.put("username", alias);
        try {
            (this.websocket = new WSClientOverride(new URI(this.assetUrl), headers)).connect();
        } catch (Exception ignored) {
        }
    }

    public void displayScreen(AbstractMenu menu) {
        menu.init();
    }

}
