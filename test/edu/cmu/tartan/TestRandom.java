package edu.cmu.tartan;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestRandom {

	private void assertTrue(String message, boolean condition) {
		org.junit.jupiter.api.Assertions.assertTrue(condition, message);
	}

	private void assertNull(Object object) {
		org.junit.jupiter.api.Assertions.assertNull(object);
	}

	private void assertNotNull(Object object) {
		org.junit.jupiter.api.Assertions.assertNotNull(object);
	}

	private void fail(String message) {
		org.junit.jupiter.api.Assertions.fail(message);
	}

    public static boolean debug = false;

    @Test
    public void test001() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test001");
        edu.cmu.tartan.client.ClientInterface.LocalModeCommand localModeCommand0 = edu.cmu.tartan.client.ClientInterface.LocalModeCommand.UNDECIDED;
        this.assertTrue("'" + localModeCommand0 + "' != '" + edu.cmu.tartan.client.ClientInterface.LocalModeCommand.UNDECIDED + "'", localModeCommand0.equals(edu.cmu.tartan.client.ClientInterface.LocalModeCommand.UNDECIDED));
    }

    @Test
    public void test002() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test002");
        java.lang.String str0 = edu.cmu.tartan.LocalGame.SAVE_FILE_NAME;
        this.assertTrue("'" + str0 + "' != '" + "Tartan_save_file.dat" + "'", str0.equals("Tartan_save_file.dat"));
    }

    @Test
    public void test003() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test003");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.BUNDLE_OF_DYNAMITE;
        this.assertTrue("'" + str0 + "' != '" + "bundle of dynamite" + "'", str0.equals("bundle of dynamite"));
    }

    @Test
    public void test004() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test004");
        edu.cmu.tartan.socket.CommandResult commandResult0 = edu.cmu.tartan.socket.CommandResult.REGISTER_FAIL;
        this.assertTrue("'" + commandResult0 + "' != '" + edu.cmu.tartan.socket.CommandResult.REGISTER_FAIL + "'", commandResult0.equals(edu.cmu.tartan.socket.CommandResult.REGISTER_FAIL));
    }

    @Test
    public void test005() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test005");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.VENDING_MACHINE_DESC;
        this.assertTrue("'" + str0 + "' != '" + "vending machine with assorted candies and treats" + "'", str0.equals("vending machine with assorted candies and treats"));
    }

    @Test
    public void test006() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test006");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.CLAY_POT;
        this.assertTrue("'" + str0 + "' != '" + "clay pot" + "'", str0.equals("clay pot"));
    }

    @Test
    public void test007() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test007");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.MICROWAVE_DESC;
        this.assertTrue("'" + str0 + "' != '" + "microwave that stinks of month old popcorn" + "'", str0.equals("microwave that stinks of month old popcorn"));
    }

    @Test
    public void test008() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test008");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_EAST;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_EAST + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_EAST));
    }

    @Test
    public void test009() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test009");
        java.util.logging.Logger logger0 = edu.cmu.tartan.Main.gameLogger;
        this.assertNotNull(logger0);
    }

    @Test
    public void test010() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test010");
        edu.cmu.tartan.socket.CommandResult commandResult0 = edu.cmu.tartan.socket.CommandResult.LOGIN_SUCCESS;
        this.assertTrue("'" + commandResult0 + "' != '" + edu.cmu.tartan.socket.CommandResult.LOGIN_SUCCESS + "'", commandResult0.equals(edu.cmu.tartan.socket.CommandResult.LOGIN_SUCCESS));
    }

    @Test
    public void test011() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test011");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.CPU;
        this.assertTrue("'" + str0 + "' != '" + "cpu" + "'", str0.equals("cpu"));
    }

    @Test
    public void test012() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test012");
        java.util.logging.Level level0 = edu.cmu.tartan.config.Config.getLogLeve();
        this.assertNotNull(level0);
    }

    @Test
    public void test013() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test013");
        edu.cmu.tartan.xml.XmlMessageType xmlMessageType0 = edu.cmu.tartan.xml.XmlMessageType.SEND_COMMAND;
        this.assertTrue("'" + xmlMessageType0 + "' != '" + edu.cmu.tartan.xml.XmlMessageType.SEND_COMMAND + "'", xmlMessageType0.equals(edu.cmu.tartan.xml.XmlMessageType.SEND_COMMAND));
    }

    @Test
    public void test014() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test014");
        edu.cmu.tartan.xml.XmlParseResult xmlParseResult0 = edu.cmu.tartan.xml.XmlParseResult.INVALID_DATA;
        this.assertTrue("'" + xmlParseResult0 + "' != '" + edu.cmu.tartan.xml.XmlParseResult.INVALID_DATA + "'", xmlParseResult0.equals(edu.cmu.tartan.xml.XmlParseResult.INVALID_DATA));
    }

    @Test
    public void test015() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test015");
        edu.cmu.tartan.xml.XmlParseResult xmlParseResult0 = edu.cmu.tartan.xml.XmlParseResult.UNKNOWN_MESSAGE;
        this.assertTrue("'" + xmlParseResult0 + "' != '" + edu.cmu.tartan.xml.XmlParseResult.UNKNOWN_MESSAGE + "'", xmlParseResult0.equals(edu.cmu.tartan.xml.XmlParseResult.UNKNOWN_MESSAGE));
    }

    @Test
    public void test016() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test016");
        edu.cmu.tartan.xml.XmlNgReason xmlNgReason0 = edu.cmu.tartan.xml.XmlNgReason.OK;
        this.assertTrue("'" + xmlNgReason0 + "' != '" + edu.cmu.tartan.xml.XmlNgReason.OK + "'", xmlNgReason0.equals(edu.cmu.tartan.xml.XmlNgReason.OK));
    }

    @Test
    public void test017() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test017");
        edu.cmu.tartan.socket.CommandResult commandResult0 = edu.cmu.tartan.socket.CommandResult.END_GAME_ALL_USER;
        this.assertTrue("'" + commandResult0 + "' != '" + edu.cmu.tartan.socket.CommandResult.END_GAME_ALL_USER + "'", commandResult0.equals(edu.cmu.tartan.socket.CommandResult.END_GAME_ALL_USER));
    }

    @Test
    public void test018() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test018");
        edu.cmu.tartan.manager.SocketMessage socketMessage2 = new edu.cmu.tartan.manager.SocketMessage("clay pot", "Tartan_save_file.dat");
    }

    @Test
    public void test019() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test019");
        java.lang.String[] strArray4 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot6 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray4, "Tartan_save_file.dat");
        java.lang.String str7 = itemClayPot6.toString();
        java.lang.String str8 = itemClayPot6.toString();
        this.assertNotNull(strArray4);
        this.assertTrue("'" + str7 + "' != '" + "Tartan_save_file.dat" + "'", str7.equals("Tartan_save_file.dat"));
        this.assertTrue("'" + str8 + "' != '" + "Tartan_save_file.dat" + "'", str8.equals("Tartan_save_file.dat"));
    }

    @Test
    public void test020() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test020");
        java.lang.String str0 = edu.cmu.tartan.GamePlayMessage.TAKEN;
        this.assertTrue("'" + str0 + "' != '" + "Taken" + "'", str0.equals("Taken"));
    }

    @Test
    public void test021() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test021");
        edu.cmu.tartan.PlayerInterpreter playerInterpreter0 = new edu.cmu.tartan.PlayerInterpreter();
    }

    @Test
    public void test022() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test022");
        edu.cmu.tartan.xml.XmlResultString xmlResultString0 = edu.cmu.tartan.xml.XmlResultString.NG;
        this.assertTrue("'" + xmlResultString0 + "' != '" + edu.cmu.tartan.xml.XmlResultString.NG + "'", xmlResultString0.equals(edu.cmu.tartan.xml.XmlResultString.NG));
    }

    @Test
    public void test023() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test023");
        edu.cmu.tartan.xml.GameMode gameMode0 = edu.cmu.tartan.xml.GameMode.LOCAL;
        this.assertTrue("'" + gameMode0 + "' != '" + edu.cmu.tartan.xml.GameMode.LOCAL + "'", gameMode0.equals(edu.cmu.tartan.xml.GameMode.LOCAL));
    }

    @Test
    public void test024() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test024");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread3 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1, "bundle of dynamite");
        try {
            boolean boolean5 = designerClientThread3.sendMessage("cpu");
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test025() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test025");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_DROP;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DROP + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_DROP));
    }

    @Test
    public void test026() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test026");
        edu.cmu.tartan.xml.XmlNgReason xmlNgReason0 = edu.cmu.tartan.xml.XmlNgReason.SERVER_BUSY;
        this.assertTrue("'" + xmlNgReason0 + "' != '" + edu.cmu.tartan.xml.XmlNgReason.SERVER_BUSY + "'", xmlNgReason0.equals(edu.cmu.tartan.xml.XmlNgReason.SERVER_BUSY));
    }

    @Test
    public void test027() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test027");
        edu.cmu.tartan.client.Client client0 = new edu.cmu.tartan.client.Client();
    }

    @Test
    public void test028() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test028");
        edu.cmu.tartan.xml.XmlResponseGameEnd xmlResponseGameEnd0 = new edu.cmu.tartan.xml.XmlResponseGameEnd();
        org.w3c.dom.Document document1 = null;
        try {
            edu.cmu.tartan.xml.XmlParseResult xmlParseResult2 = xmlResponseGameEnd0.doYourJob(document1);
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test029() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test029");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.DIAMOND;
        this.assertTrue("'" + str0 + "' != '" + "diamond" + "'", str0.equals("diamond"));
    }

    @Test
    public void test030() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test030");
        java.lang.String[] strArray6 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray6, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemFlashlight itemFlashlight10 = new edu.cmu.tartan.item.ItemFlashlight("", "Taken", strArray6, "Tartan_save_file.dat");
        this.assertNotNull(strArray6);
    }

    @Test
    public void test031() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test031");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.APPLIANCE;
        this.assertTrue("'" + str0 + "' != '" + "appliance" + "'", str0.equals("appliance"));
    }

    @Test
    public void test032() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test032");
        int int0 = edu.cmu.tartan.config.Config.getDesignerPort();
        this.assertTrue("'" + int0 + "' != '" + 10016 + "'", int0 == 10016);
    }

    @Test
    public void test033() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test033");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread3 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1, "bundle of dynamite");
        try {
            boolean boolean4 = designerClientThread3.stopSocket();
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test034() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test034");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.BUTTON;
        this.assertTrue("'" + str0 + "' != '" + "Button" + "'", str0.equals("Button"));
    }

    @Test
    public void test035() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test035");
        edu.cmu.tartan.xml.XmlResponseCommand xmlResponseCommand0 = new edu.cmu.tartan.xml.XmlResponseCommand();
        org.w3c.dom.Document document2 = null;
        try {
            org.w3c.dom.NodeList nodeList3 = xmlResponseCommand0.getNodeListOfGivenTag("Tartan_save_file.dat", document2);
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test036() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test036");
        java.lang.String str0 = edu.cmu.tartan.GamePlayMessage.I_DO_NOT_UNDERSTAND;
        this.assertTrue("'" + str0 + "' != '" + "I don't understand that.\n" + "'", str0.equals("I don't understand that.\n"));
    }

    @Test
    public void test037() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test037");
        int int0 = edu.cmu.tartan.room.RoomElevator.EV_MOVE_TIME;
        this.assertTrue("'" + int0 + "' != '" + 100 + "'", int0 == 100);
    }

    @Test
    public void test038() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test038");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_REMOVE;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_REMOVE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_REMOVE));
    }

    @Test
    public void test039() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test039");
        edu.cmu.tartan.socket.CommandResult commandResult0 = edu.cmu.tartan.socket.CommandResult.UPLOAD_FAIL;
        this.assertTrue("'" + commandResult0 + "' != '" + edu.cmu.tartan.socket.CommandResult.UPLOAD_FAIL + "'", commandResult0.equals(edu.cmu.tartan.socket.CommandResult.UPLOAD_FAIL));
    }

    @Test
    public void test040() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test040");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_VIEW_ITEMS;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_VIEW_ITEMS + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_VIEW_ITEMS));
    }

    @Test
    public void test041() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test041");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.ELEVATOR_FLOOR_2;
        this.assertTrue("'" + str0 + "' != '" + "Elevator Floor 2 Button" + "'", str0.equals("Elevator Floor 2 Button"));
    }

    @Test
    public void test042() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test042");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.FRIDGE;
        this.assertTrue("'" + str0 + "' != '" + "fridge" + "'", str0.equals("fridge"));
    }

    @Test
    public void test043() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test043");
        org.w3c.dom.Document document0 = null;
        java.lang.String str1 = edu.cmu.tartan.xml.XmlWriter.convertDocumentToString(document0);
        this.assertTrue("'" + str1 + "' != '" + "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + "'", str1.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
    }

    @Test
    public void test044() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test044");
        edu.cmu.tartan.xml.XmlNgReason xmlNgReason0 = edu.cmu.tartan.xml.XmlNgReason.NO_PLAYERS;
        this.assertTrue("'" + xmlNgReason0 + "' != '" + edu.cmu.tartan.xml.XmlNgReason.NO_PLAYERS + "'", xmlNgReason0.equals(edu.cmu.tartan.xml.XmlNgReason.NO_PLAYERS));
    }

    @Test
    public void test045() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test045");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.MUG;
        this.assertTrue("'" + str0 + "' != '" + "mug" + "'", str0.equals("mug"));
    }

    @Test
    public void test046() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test046");
        edu.cmu.tartan.xml.XmlMessageType xmlMessageType0 = edu.cmu.tartan.xml.XmlMessageType.HEART_BEAT;
        this.assertTrue("'" + xmlMessageType0 + "' != '" + edu.cmu.tartan.xml.XmlMessageType.HEART_BEAT + "'", xmlMessageType0.equals(edu.cmu.tartan.xml.XmlMessageType.HEART_BEAT));
    }

    @Test
    public void test047() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test047");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.FLOOR_3_BUTTON;
        this.assertTrue("'" + str0 + "' != '" + "Floor 3 Button" + "'", str0.equals("Floor 3 Button"));
    }

    @Test
    public void test048() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test048");
        edu.cmu.tartan.action.Type type0 = edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT;
        this.assertTrue("'" + type0 + "' != '" + edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT + "'", type0.equals(edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT));
    }

    @Test
    public void test049() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test049");
        edu.cmu.tartan.server.ServerInterface.Command command0 = edu.cmu.tartan.server.ServerInterface.Command.UNDECIDED;
        this.assertTrue("'" + command0 + "' != '" + edu.cmu.tartan.server.ServerInterface.Command.UNDECIDED + "'", command0.equals(edu.cmu.tartan.server.ServerInterface.Command.UNDECIDED));
    }

    @Test
    public void test050() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test050");
        edu.cmu.tartan.xml.XmlMessageType xmlMessageType0 = edu.cmu.tartan.xml.XmlMessageType.REQ_GAME_START;
        this.assertTrue("'" + xmlMessageType0 + "' != '" + edu.cmu.tartan.xml.XmlMessageType.REQ_GAME_START + "'", xmlMessageType0.equals(edu.cmu.tartan.xml.XmlMessageType.REQ_GAME_START));
    }

    @Test
    public void test051() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test051");
        edu.cmu.tartan.xml.XmlWriter.saveXmlStringToFile("", "vending machine with assorted candies and treats");
    }

    @Test
    public void test052() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test052");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_NORTH;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_NORTH + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_NORTH));
    }

    @Test
    public void test053() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test053");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread3 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1, "bundle of dynamite");
        java.lang.String str4 = designerClientThread3.getThreadName();
        try {
            boolean boolean5 = designerClientThread3.stopSocket();
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
        this.assertTrue("'" + str4 + "' != '" + "bundle of dynamite" + "'", str4.equals("bundle of dynamite"));
    }

    @Test
    public void test054() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test054");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        boolean boolean40 = player38.setUserName("Elevator Floor 2 Button");
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertTrue("'" + boolean40 + "' != '" + true + "'", boolean40 == true);
    }

    @Test
    public void test055() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test055");
        java.lang.String[] strArray2 = null;
        edu.cmu.tartan.item.ItemCoffee itemCoffee4 = new edu.cmu.tartan.item.ItemCoffee("", "vending machine with assorted candies and treats", strArray2, "");
        java.lang.String[] strArray5 = itemCoffee4.getAliases();
        this.assertNull(strArray5);
    }

    @Test
    public void test056() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test056");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        roomRequiredItem4.setDescription("I don't understand that.\n");
    }

    @Test
    public void test057() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test057");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.DYNAMITE;
        this.assertTrue("'" + str0 + "' != '" + "dynamite" + "'", str0.equals("dynamite"));
    }

    @Test
    public void test059() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test059");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.SECRET_DOCUMENT;
        this.assertTrue("'" + str0 + "' != '" + "Secret document" + "'", str0.equals("Secret document"));
    }

    @Test
    public void test060() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test060");
        edu.cmu.tartan.TerminateGameException terminateGameException1 = new edu.cmu.tartan.TerminateGameException("hi!");
        java.lang.String str2 = terminateGameException1.toString();
        this.assertTrue("'" + str2 + "' != '" + "edu.cmu.tartan.TerminateGameException: hi!" + "'", str2.equals("edu.cmu.tartan.TerminateGameException: hi!"));
    }

    @Test
    public void test061() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test061");
        java.lang.String[] strArray8 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot10 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray8, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader12 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray8, "");
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine14 = new edu.cmu.tartan.item.ItemVendingMachine("diamond", "vending machine with assorted candies and treats", strArray8, "cpu");
        edu.cmu.tartan.games.InvalidGameException invalidGameException16 = new edu.cmu.tartan.games.InvalidGameException("Floor 3 Button");
        boolean boolean17 = itemVendingMachine14.equals(invalidGameException16);
        itemVendingMachine14.setCount((byte) 0);
        this.assertNotNull(strArray8);
        this.assertTrue("'" + boolean17 + "' != '" + false + "'", boolean17 == false);
    }

    @Test
    public void test062() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test062");
        edu.cmu.tartan.xml.XmlMessageType xmlMessageType0 = edu.cmu.tartan.xml.XmlMessageType.GAME_END;
        this.assertTrue("'" + xmlMessageType0 + "' != '" + edu.cmu.tartan.xml.XmlMessageType.GAME_END + "'", xmlMessageType0.equals(edu.cmu.tartan.xml.XmlMessageType.GAME_END));
    }

    @Test
    public void test063() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test063");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.VENDOR;
        this.assertTrue("'" + str0 + "' != '" + "vendor" + "'", str0.equals("vendor"));
    }

    @Test
    public void test064() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test064");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        edu.cmu.tartan.Player player40 = new edu.cmu.tartan.Player(roomRequiredItem4, "vending machine with assorted candies and treats");
        java.lang.String[] strArray45 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot47 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray45, "Tartan_save_file.dat");
        boolean boolean48 = itemClayPot47.disappears();
        player40.score(itemClayPot47);
        try {
            int int51 = itemClayPot47.compareTo("Secret document");
            this.fail("Expected exception of type java.lang.ClassCastException; message: java.lang.String cannot be cast to edu.cmu.tartan.item.Item");
        } catch (java.lang.ClassCastException e) {
        }
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertNotNull(strArray45);
        this.assertTrue("'" + boolean48 + "' != '" + false + "'", boolean48 == false);
    }

    @Test
    public void test065() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test065");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        roomRequiredItem4.setLoseMessage("Taken");
        try {
            int int8 = roomRequiredItem4.compareTo((byte) 100);
            this.fail("Expected exception of type java.lang.ClassCastException; message: java.lang.Byte cannot be cast to edu.cmu.tartan.room.Room");
        } catch (java.lang.ClassCastException e) {
        }
    }

    @Test
    public void test066() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test066");
        java.lang.String[] strArray6 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray6, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader10 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray6, "");
        java.lang.String[] strArray15 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot17 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray15, "Tartan_save_file.dat");
        boolean boolean18 = itemClayPot17.disappears();
        itemClayPot17.destroy();
        boolean boolean20 = itemKeycardReader10.uninstall(itemClayPot17);
        edu.cmu.tartan.item.Item item21 = itemClayPot17.relatedItem();
        this.assertNotNull(strArray6);
        this.assertNotNull(strArray15);
        this.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        this.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        this.assertNull(item21);
    }

    @Test
    public void test067() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test067");
        boolean boolean2 = edu.cmu.tartan.xml.XmlWriter.overWriteFile("vendor", "Button");
        this.assertTrue("'" + boolean2 + "' != '" + false + "'", boolean2 == false);
    }

    @Test
    public void test068() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test068");
        edu.cmu.tartan.xml.XmlResponseGameEnd xmlResponseGameEnd0 = new edu.cmu.tartan.xml.XmlResponseGameEnd();
        edu.cmu.tartan.xml.XmlMessageType xmlMessageType1 = xmlResponseGameEnd0.getMsgType();
        this.assertTrue("'" + xmlMessageType1 + "' != '" + edu.cmu.tartan.xml.XmlMessageType.REQ_GAME_END + "'", xmlMessageType1.equals(edu.cmu.tartan.xml.XmlMessageType.REQ_GAME_END));
    }

    @Test
    public void test069() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test069");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_BURN;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_BURN + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_BURN));
    }

    @Test
    public void test070() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test070");
        java.io.Closeable closeable0 = null;
        edu.cmu.tartan.xml.XmlWriter.close(closeable0);
    }

    @Test
    public void test071() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test071");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        boolean boolean40 = player38.setUserName("fridge");
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertTrue("'" + boolean40 + "' != '" + true + "'", boolean40 == true);
    }

    @Test
    public void test072() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test072");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.BEVERAGE;
        this.assertTrue("'" + str0 + "' != '" + "beverage" + "'", str0.equals("beverage"));
    }

    @Test
    public void test073() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test073");
        java.lang.String[] strArray8 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot10 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray8, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader12 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray8, "");
        edu.cmu.tartan.item.ItemFood itemFood14 = new edu.cmu.tartan.item.ItemFood("fridge", "edu.cmu.tartan.TerminateGameException: hi!", strArray8, "diamond");
        this.assertNotNull(strArray8);
    }

    @Test
    public void test074() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test074");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_SOUTHWEST;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_SOUTHWEST + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_SOUTHWEST));
    }

    @Test
    public void test075() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test075");
        java.lang.String[] strArray10 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot12 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray10, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader14 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray10, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond16 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray10, "bundle of dynamite");
        edu.cmu.tartan.item.ItemLock itemLock18 = new edu.cmu.tartan.item.ItemLock("vendor", "You cannot visit this room", strArray10, "Taken");
        this.assertNotNull(strArray10);
    }

    @Test
    public void test076() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test076");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        edu.cmu.tartan.Player player40 = new edu.cmu.tartan.Player(roomRequiredItem4, "vending machine with assorted candies and treats");
        java.util.ArrayList<edu.cmu.tartan.goal.GameGoal> gameGoalList41 = player40.getGoals();
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertNotNull(gameGoalList41);
    }

    @Test
    public void test077() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test077");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.METAL_KEYCARD_READER;
        this.assertTrue("'" + str0 + "' != '" + "metal keycard reader" + "'", str0.equals("metal keycard reader"));
    }

    @Test
    public void test078() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test078");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.BRICK;
        this.assertTrue("'" + str0 + "' != '" + "brick" + "'", str0.equals("brick"));
    }

    @Test
    public void test079() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test079");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.JEWEL;
        this.assertTrue("'" + str0 + "' != '" + "jewel" + "'", str0.equals("jewel"));
    }

    @Test
    public void test080() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test080");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.LOCK;
        this.assertTrue("'" + str0 + "' != '" + "lock" + "'", str0.equals("lock"));
    }

    @Test
    public void test081() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test081");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.VENTILATION_FAN;
        this.assertTrue("'" + str0 + "' != '" + "ventilation fan" + "'", str0.equals("ventilation fan"));
    }

    @Test
    public void test082() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test082");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_DIE;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DIE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_DIE));
    }

    @Test
    public void test083() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test083");
        java.lang.String str0 = edu.cmu.tartan.GamePlayMessage.SAVE_FAILURE_2_3;
        this.assertTrue("'" + str0 + "' != '" + "Uh-oh. Error is occurred. Please retry.\n" + "'", str0.equals("Uh-oh. Error is occurred. Please retry.\n"));
    }

    @Test
    public void test084() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test084");
        edu.cmu.tartan.Main main0 = new edu.cmu.tartan.Main();
    }

    @Test
    public void test085() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test085");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_NORTHWEST;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_NORTHWEST + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_NORTHWEST));
    }

    @Test
    public void test086() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test086");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.LADDER;
        this.assertTrue("'" + str0 + "' != '" + "ladder" + "'", str0.equals("ladder"));
    }

    @Test
    public void test087() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test087");
        java.lang.String[] strArray6 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray6, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader10 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray6, "");
        java.lang.String[] strArray15 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot17 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray15, "Tartan_save_file.dat");
        boolean boolean18 = itemClayPot17.disappears();
        itemClayPot17.destroy();
        boolean boolean20 = itemKeycardReader10.uninstall(itemClayPot17);
        edu.cmu.tartan.room.Room room21 = itemClayPot17.relatedRoom();
        this.assertNotNull(strArray6);
        this.assertNotNull(strArray15);
        this.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        this.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        this.assertNull(room21);
    }

    @Test
    public void test088() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test088");
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler0 = null;
        edu.cmu.tartan.socket.SocketServer socketServer1 = new edu.cmu.tartan.socket.SocketServer(iQueueHandler0);
        boolean boolean5 = socketServer1.login(false, "vending machine with assorted candies and treats", "hi!");
        boolean boolean6 = socketServer1.stopSocket();
        this.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
        this.assertTrue("'" + boolean6 + "' != '" + true + "'", boolean6 == true);
    }

    @Test
    public void test089() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test089");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        java.lang.String str39 = roomRequiredItem4.description();
        roomRequiredItem4.setDescription("appliance");
        java.lang.String[] strArray48 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot50 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray48, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader52 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray48, "");
        boolean boolean53 = roomRequiredItem4.equals(itemKeycardReader52);
        roomRequiredItem4.setLoseMessage("dynamite");
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertTrue("'" + str39 + "' != '" + "You cannot visit this room" + "'", str39.equals("You cannot visit this room"));
        this.assertNotNull(strArray48);
        this.assertTrue("'" + boolean53 + "' != '" + false + "'", boolean53 == false);
    }

    @Test
    public void test090() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test090");
        java.lang.String[] strArray14 = new java.lang.String[] { "vending machine with assorted candies and treats", "cpu", "edu.cmu.tartan.TerminateGameException: hi!", "Taken", "clay pot", "Taken", "Tartan_save_file.dat", "cpu", "Floor 3 Button", "fridge", "Floor 3 Button", "Elevator Floor 2 Button", "dynamite", "I don't understand that.\n" };
        java.util.ArrayList<java.lang.String> strList15 = new java.util.ArrayList<java.lang.String>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList15, strArray14);
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem21 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray30 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot32 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray30, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader34 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray30, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond36 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray30, "bundle of dynamite");
        java.lang.String[] strArray41 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot43 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray41, "Tartan_save_file.dat");
        java.lang.String[] strArray48 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot50 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray48, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray51 = new edu.cmu.tartan.item.Item[] { itemDiamond36, itemClayPot43, itemClayPot50 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList52 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean53 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList52, itemArray51);
        edu.cmu.tartan.Player player55 = new edu.cmu.tartan.Player(roomRequiredItem21, itemList52, "Taken");
        edu.cmu.tartan.goal.GameCollectGoal gameCollectGoal56 = new edu.cmu.tartan.goal.GameCollectGoal(strList15, player55);
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem61 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray70 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot72 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray70, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader74 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray70, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond76 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray70, "bundle of dynamite");
        java.lang.String[] strArray81 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot83 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray81, "Tartan_save_file.dat");
        java.lang.String[] strArray88 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot90 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray88, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray91 = new edu.cmu.tartan.item.Item[] { itemDiamond76, itemClayPot83, itemClayPot90 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList92 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean93 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList92, itemArray91);
        edu.cmu.tartan.Player player95 = new edu.cmu.tartan.Player(roomRequiredItem61, itemList92, "Taken");
        roomRequiredItem61.setPlayerDiesOnItemDiscard(false);
        player55.move(roomRequiredItem61);
        this.assertNotNull(strArray14);
        this.assertTrue("'" + boolean16 + "' != '" + true + "'", boolean16 == true);
        this.assertNotNull(strArray30);
        this.assertNotNull(strArray41);
        this.assertNotNull(strArray48);
        this.assertNotNull(itemArray51);
        this.assertTrue("'" + boolean53 + "' != '" + true + "'", boolean53 == true);
        this.assertNotNull(strArray70);
        this.assertNotNull(strArray81);
        this.assertNotNull(strArray88);
        this.assertNotNull(itemArray91);
        this.assertTrue("'" + boolean93 + "' != '" + true + "'", boolean93 == true);
    }

    @Test
    public void test091() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test091");
        edu.cmu.tartan.MapConfig mapConfig0 = edu.cmu.tartan.MapConfig.NO_GOAL_ROOM;
        this.assertTrue("'" + mapConfig0 + "' != '" + edu.cmu.tartan.MapConfig.NO_GOAL_ROOM + "'", mapConfig0.equals(edu.cmu.tartan.MapConfig.NO_GOAL_ROOM));
    }

    @Test
    public void test092() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test092");
        edu.cmu.tartan.xml.XmlResponseHeartBeat xmlResponseHeartBeat0 = new edu.cmu.tartan.xml.XmlResponseHeartBeat();
    }

    @Disabled
    @Test
    public void test093() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test093");
        java.lang.String str0 = edu.cmu.tartan.config.Config.getDbName();
        this.assertNull(str0);
    }

    @Test
    public void test094() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test094");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.GOLD_LOCK;
        this.assertTrue("'" + str0 + "' != '" + "gold lock" + "'", str0.equals("gold lock"));
    }

    @Test
    public void test095() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test095");
        edu.cmu.tartan.server.Server server0 = new edu.cmu.tartan.server.Server();
    }

    @Test
    public void test096() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test096");
        java.lang.String[] strArray12 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot14 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray12, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader16 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray12, "");
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine18 = new edu.cmu.tartan.item.ItemVendingMachine("diamond", "vending machine with assorted candies and treats", strArray12, "cpu");
        edu.cmu.tartan.item.ItemUnknown itemUnknown20 = new edu.cmu.tartan.item.ItemUnknown("hi!", "bundle of dynamite", strArray12, "vending machine with assorted candies and treats");
        edu.cmu.tartan.item.ItemButton itemButton22 = new edu.cmu.tartan.item.ItemButton("mug", "Secret document", strArray12, "I don't understand that.\n");
        this.assertNotNull(strArray12);
    }

    @Test
    public void test097() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test097");
        edu.cmu.tartan.ServerGame serverGame1 = new edu.cmu.tartan.ServerGame("microwave that stinks of month old popcorn");
        boolean boolean2 = serverGame1.handleQuit();
        this.assertTrue("'" + boolean2 + "' != '" + true + "'", boolean2 == true);
    }

    @Test
    public void test098() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test098");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.FLOOR_1_BUTTON;
        this.assertTrue("'" + str0 + "' != '" + "Floor 1 Button" + "'", str0.equals("Floor 1 Button"));
    }

    @Test
    public void test099() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test099");
        edu.cmu.tartan.xml.XmlResponseCommand xmlResponseCommand0 = new edu.cmu.tartan.xml.XmlResponseCommand();
        org.w3c.dom.Document document1 = null;
        try {
            edu.cmu.tartan.xml.XmlParseResult xmlParseResult2 = xmlResponseCommand0.parsingCommandInfo(document1);
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test100() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test100");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.FLASHLIGTH_MESSAGE;
        this.assertTrue("'" + str0 + "' != '" + "battery operated flashlight" + "'", str0.equals("battery operated flashlight"));
    }

    @Test
    public void test101() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test101");
        edu.cmu.tartan.manager.ResponseMessage responseMessage0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.SocketClient socketClient3 = new edu.cmu.tartan.socket.SocketClient(responseMessage0, iQueueHandler1, true);
    }

    @Test
    public void test102() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test102");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_SHAKE;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_SHAKE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_SHAKE));
    }

    @Test
    public void test103() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test103");
        edu.cmu.tartan.client.ClientInterface.RunningMode runningMode0 = edu.cmu.tartan.client.ClientInterface.RunningMode.LOCAL;
        this.assertTrue("'" + runningMode0 + "' != '" + edu.cmu.tartan.client.ClientInterface.RunningMode.LOCAL + "'", runningMode0.equals(edu.cmu.tartan.client.ClientInterface.RunningMode.LOCAL));
    }

    @Test
    public void test104() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test104");
        edu.cmu.tartan.MapConfig mapConfig0 = edu.cmu.tartan.MapConfig.OK;
        this.assertTrue("'" + mapConfig0 + "' != '" + edu.cmu.tartan.MapConfig.OK + "'", mapConfig0.equals(edu.cmu.tartan.MapConfig.OK));
    }

    @Test
    public void test105() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test105");
        edu.cmu.tartan.client.ClientInterface.LocalModeCommand localModeCommand0 = edu.cmu.tartan.client.ClientInterface.LocalModeCommand.NEW;
        this.assertTrue("'" + localModeCommand0 + "' != '" + edu.cmu.tartan.client.ClientInterface.LocalModeCommand.NEW + "'", localModeCommand0.equals(edu.cmu.tartan.client.ClientInterface.LocalModeCommand.NEW));
    }

    @Test
    public void test106() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test106");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_DIG;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DIG + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_DIG));
    }

    @Test
    public void test107() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test107");
        edu.cmu.tartan.client.ClientInterface.NetworkModeCommand networkModeCommand0 = edu.cmu.tartan.client.ClientInterface.NetworkModeCommand.UNDECIDED;
        this.assertTrue("'" + networkModeCommand0 + "' != '" + edu.cmu.tartan.client.ClientInterface.NetworkModeCommand.UNDECIDED + "'", networkModeCommand0.equals(edu.cmu.tartan.client.ClientInterface.NetworkModeCommand.UNDECIDED));
    }

    @Test
    public void test108() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test108");
        edu.cmu.tartan.GameInterface.MessageType messageType0 = edu.cmu.tartan.GameInterface.MessageType.PUBLIC;
        this.assertTrue("'" + messageType0 + "' != '" + edu.cmu.tartan.GameInterface.MessageType.PUBLIC + "'", messageType0.equals(edu.cmu.tartan.GameInterface.MessageType.PUBLIC));
    }

    @Disabled
    @Test
    public void test109() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test109");
        edu.cmu.tartan.account.AccountManager accountManager1 = new edu.cmu.tartan.account.AccountManager("Elevator Floor 2 Button");
        boolean boolean5 = accountManager1.registerUser("", "gold lock", "Floor 3 Button");
        boolean boolean9 = accountManager1.loginUser("I don't understand that.\n", "gold lock", "Tartan_save_file.dat");
        this.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
        this.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
    }

    @Test
    public void test110() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test110");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.CANDLE;
        this.assertTrue("'" + str0 + "' != '" + "candle" + "'", str0.equals("candle"));
    }

    @Test
    public void test111() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test111");
        java.lang.String[] strArray15 = new java.lang.String[] { "vending machine with assorted candies and treats", "cpu", "edu.cmu.tartan.TerminateGameException: hi!", "Taken", "clay pot", "Taken", "Tartan_save_file.dat", "cpu", "Floor 3 Button", "fridge", "Floor 3 Button", "Elevator Floor 2 Button", "dynamite", "I don't understand that.\n" };
        java.util.ArrayList<java.lang.String> strList16 = new java.util.ArrayList<java.lang.String>();
        boolean boolean17 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList16, strArray15);
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem22 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader35 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray31, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond37 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray31, "bundle of dynamite");
        java.lang.String[] strArray42 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot44 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray42, "Tartan_save_file.dat");
        java.lang.String[] strArray49 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot51 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray49, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray52 = new edu.cmu.tartan.item.Item[] { itemDiamond37, itemClayPot44, itemClayPot51 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList53 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean54 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList53, itemArray52);
        edu.cmu.tartan.Player player56 = new edu.cmu.tartan.Player(roomRequiredItem22, itemList53, "Taken");
        edu.cmu.tartan.goal.GameCollectGoal gameCollectGoal57 = new edu.cmu.tartan.goal.GameCollectGoal(strList16, player56);
        edu.cmu.tartan.goal.GamePointsGoal gamePointsGoal58 = new edu.cmu.tartan.goal.GamePointsGoal(0, player56);
        java.lang.Integer int59 = gamePointsGoal58.getWinningScore();
        this.assertNotNull(strArray15);
        this.assertTrue("'" + boolean17 + "' != '" + true + "'", boolean17 == true);
        this.assertNotNull(strArray31);
        this.assertNotNull(strArray42);
        this.assertNotNull(strArray49);
        this.assertNotNull(itemArray52);
        this.assertTrue("'" + boolean54 + "' != '" + true + "'", boolean54 == true);
        this.assertTrue("'" + int59 + "' != '" + 0 + "'", int59.equals(0));
    }

    @Test
    public void test112() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test112");
        java.lang.String str0 = edu.cmu.tartan.room.Room.DEFAULT_DESC;
        this.assertTrue("'" + str0 + "' != '" + "You are in a room" + "'", str0.equals("You are in a room"));
    }

    @Test
    public void test113() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test113");
        java.lang.String str0 = edu.cmu.tartan.Player.DEFAULT_USER_NAME;
        this.assertTrue("'" + str0 + "' != '" + "Tony" + "'", str0.equals("Tony"));
    }

    @Test
    public void test114() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test114");
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler0 = null;
        edu.cmu.tartan.socket.SocketServer socketServer1 = new edu.cmu.tartan.socket.SocketServer(iQueueHandler0);
        boolean boolean4 = socketServer1.sendToOthers("bundle of dynamite", "dynamite");
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler5 = null;
        edu.cmu.tartan.socket.SocketServer socketServer6 = new edu.cmu.tartan.socket.SocketServer(iQueueHandler5);
        boolean boolean10 = socketServer6.login(false, "vending machine with assorted candies and treats", "hi!");
        socketServer6.setIsPlaying(false);
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler13 = null;
        edu.cmu.tartan.manager.TartanGameManager tartanGameManager14 = new edu.cmu.tartan.manager.TartanGameManager(socketServer1, socketServer6, iQueueHandler13);
        this.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
        this.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
    }

    @Test
    public void test115() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test115");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        roomRequiredItem4.setPlayerDiesOnItemDiscard(false);
        edu.cmu.tartan.Player player42 = new edu.cmu.tartan.Player(roomRequiredItem4, "metal keycard reader");
        java.lang.String[] strArray51 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot53 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray51, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader55 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray51, "");
        edu.cmu.tartan.item.ItemTorch itemTorch57 = new edu.cmu.tartan.item.ItemTorch("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "", strArray51, "microwave that stinks of month old popcorn");
        boolean boolean58 = player42.grabItem(itemTorch57);
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertNotNull(strArray51);
        this.assertTrue("'" + boolean58 + "' != '" + true + "'", boolean58 == true);
    }

    @Test
    public void test116() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test116");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.UserClientThread userClientThread3 = new edu.cmu.tartan.socket.UserClientThread(socket0, iQueueHandler1, "microwave that stinks of month old popcorn");
        try {
            userClientThread3.run();
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test117() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test117");
        edu.cmu.tartan.action.Type type0 = edu.cmu.tartan.action.Type.TYPE_HASNOOBJECT;
        this.assertTrue("'" + type0 + "' != '" + edu.cmu.tartan.action.Type.TYPE_HASNOOBJECT + "'", type0.equals(edu.cmu.tartan.action.Type.TYPE_HASNOOBJECT));
    }

    @Test
    public void test118() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test118");
        edu.cmu.tartan.xml.XmlResponseCommand xmlResponseCommand0 = new edu.cmu.tartan.xml.XmlResponseCommand();
        edu.cmu.tartan.xml.XmlMessageType xmlMessageType1 = xmlResponseCommand0.getMsgType();
        this.assertTrue("'" + xmlMessageType1 + "' != '" + edu.cmu.tartan.xml.XmlMessageType.SEND_COMMAND + "'", xmlMessageType1.equals(edu.cmu.tartan.xml.XmlMessageType.SEND_COMMAND));
    }

    @Test
    public void test119() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test119");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.ONE;
        this.assertTrue("'" + str0 + "' != '" + "1" + "'", str0.equals("1"));
    }

    @Test
    public void test120() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test120");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        boolean boolean40 = player38.score((byte) -1);
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem45 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray54 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot56 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray54, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader58 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray54, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond60 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray54, "bundle of dynamite");
        java.lang.String[] strArray65 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot67 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray65, "Tartan_save_file.dat");
        java.lang.String[] strArray72 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot74 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray72, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray75 = new edu.cmu.tartan.item.Item[] { itemDiamond60, itemClayPot67, itemClayPot74 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList76 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean77 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList76, itemArray75);
        edu.cmu.tartan.Player player79 = new edu.cmu.tartan.Player(roomRequiredItem45, itemList76, "Taken");
        player38.move(roomRequiredItem45);
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertTrue("'" + boolean40 + "' != '" + false + "'", boolean40 == false);
        this.assertNotNull(strArray54);
        this.assertNotNull(strArray65);
        this.assertNotNull(strArray72);
        this.assertNotNull(itemArray75);
        this.assertTrue("'" + boolean77 + "' != '" + true + "'", boolean77 == true);
    }

    @Test
    public void test121() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test121");
        java.lang.String str0 = edu.cmu.tartan.item.StringForItems.EXPLOSIVE;
        this.assertTrue("'" + str0 + "' != '" + "explosive" + "'", str0.equals("explosive"));
    }

    @Test
    public void test122() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test122");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.UserClientThread userClientThread3 = new edu.cmu.tartan.socket.UserClientThread(socket0, iQueueHandler1, "microwave that stinks of month old popcorn");
        try {
            boolean boolean5 = userClientThread3.sendMessage("Tony");
            this.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test123() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test123");
        java.lang.String[] strArray5 = new java.lang.String[] { "metal keycard reader", "jewel", "mug" };
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave7 = new edu.cmu.tartan.item.ItemMicrowave("", "beverage", strArray5, "bundle of dynamite");
        java.lang.String[] strArray18 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot20 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray18, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader22 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray18, "");
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine24 = new edu.cmu.tartan.item.ItemVendingMachine("diamond", "vending machine with assorted candies and treats", strArray18, "cpu");
        edu.cmu.tartan.item.ItemUnknown itemUnknown26 = new edu.cmu.tartan.item.ItemUnknown("hi!", "bundle of dynamite", strArray18, "vending machine with assorted candies and treats");
        java.lang.String str27 = itemUnknown26.detailDescription();
        boolean boolean28 = itemMicrowave7.install(itemUnknown26);
        edu.cmu.tartan.room.Room room29 = itemMicrowave7.relatedRoom();
        this.assertNotNull(strArray5);
        this.assertNotNull(strArray18);
        this.assertTrue("'" + str27 + "' != '" + "bundle of dynamite" + "'", str27.equals("bundle of dynamite"));
        this.assertTrue("'" + boolean28 + "' != '" + true + "'", boolean28 == true);
        this.assertNull(room29);
    }

    @Test
    public void test124() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test124");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        edu.cmu.tartan.Player player40 = new edu.cmu.tartan.Player(roomRequiredItem4, "vending machine with assorted candies and treats");
        java.lang.String[] strArray47 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot49 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray47, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader51 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray47, "");
        boolean boolean52 = player40.pickup(itemKeycardReader51);
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertNotNull(strArray47);
        this.assertTrue("'" + boolean52 + "' != '" + true + "'", boolean52 == true);
    }

    @Test
    public void test125() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test125");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
    }

    @Test
    public void test126() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test126");
        edu.cmu.tartan.account.AccountManager accountManager1 = new edu.cmu.tartan.account.AccountManager("Elevator Floor 2 Button");
        edu.cmu.tartan.account.ReturnType returnType3 = accountManager1.validatePassword("Floor 3 Button");
        this.assertTrue("'" + returnType3 + "' != '" + edu.cmu.tartan.account.ReturnType.INVALID_PW + "'", returnType3.equals(edu.cmu.tartan.account.ReturnType.INVALID_PW));
    }

    @Disabled
    @Test
    public void test127() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test127");
        java.lang.String[] strArray8 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot10 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray8, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader12 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray8, "");
        edu.cmu.tartan.item.ItemTorch itemTorch14 = new edu.cmu.tartan.item.ItemTorch("", "Tartan_save_file.dat", strArray8, "Taken");
        edu.cmu.tartan.account.AccountManager accountManager16 = new edu.cmu.tartan.account.AccountManager("Elevator Floor 2 Button");
        boolean boolean20 = accountManager16.registerUser("", "gold lock", "Floor 3 Button");
        boolean boolean21 = itemTorch14.equals(accountManager16);
        this.assertNotNull(strArray8);
        this.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        this.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
    }

    @Test
    public void test128() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test128");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        roomRequiredItem4.setDescription("ladder");
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
    }

    @Test
    public void test129() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test129");
        java.lang.String[] strArray4 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot6 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray4, "Tartan_save_file.dat");
        itemClayPot6.setDestroyMessage("clay pot");
        edu.cmu.tartan.item.Item item9 = itemClayPot6.installedItem();
        itemClayPot6.destroy();
        this.assertNotNull(strArray4);
        this.assertNull(item9);
    }

    @Test
    public void test130() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test130");
        java.lang.String[] strArray12 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot14 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray12, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader16 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray12, "");
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine18 = new edu.cmu.tartan.item.ItemVendingMachine("diamond", "vending machine with assorted candies and treats", strArray12, "cpu");
        edu.cmu.tartan.item.ItemUnknown itemUnknown20 = new edu.cmu.tartan.item.ItemUnknown("hi!", "bundle of dynamite", strArray12, "vending machine with assorted candies and treats");
        edu.cmu.tartan.item.ItemDocument itemDocument22 = new edu.cmu.tartan.item.ItemDocument("Floor 3 Button", "diamond", strArray12, "bundle of dynamite");
        itemDocument22.setDescription("I don't understand that.\n");
        java.lang.String str25 = itemDocument22.description();
        this.assertNotNull(strArray12);
        this.assertTrue("'" + str25 + "' != '" + "I don't understand that.\n" + "'", str25.equals("I don't understand that.\n"));
    }

    @Test
    public void test131() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test131");
        java.lang.String[] strArray2 = null;
        edu.cmu.tartan.item.ItemCoffee itemCoffee4 = new edu.cmu.tartan.item.ItemCoffee("", "vending machine with assorted candies and treats", strArray2, "");
        itemCoffee4.eat();
    }

    @Test
    public void test132() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test132");
        edu.cmu.tartan.server.ServerInterface serverInterface0 = new edu.cmu.tartan.server.ServerInterface();
    }

    @Test
    public void test133() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test133");
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler0 = null;
        edu.cmu.tartan.socket.SocketServer socketServer1 = new edu.cmu.tartan.socket.SocketServer(iQueueHandler0);
        boolean boolean5 = socketServer1.login(false, "vending machine with assorted candies and treats", "hi!");
        boolean boolean8 = socketServer1.sendToOthers("battery operated flashlight", "microwave that stinks of month old popcorn");
        this.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
        this.assertTrue("'" + boolean8 + "' != '" + false + "'", boolean8 == false);
    }

    @Test
    public void test134() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test134");
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem4 = new edu.cmu.tartan.room.RoomRequiredItem("bundle of dynamite", "Tartan_save_file.dat", "vending machine with assorted candies and treats", "clay pot");
        java.lang.String[] strArray13 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot15 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray13, "Tartan_save_file.dat");
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader17 = new edu.cmu.tartan.item.ItemKeycardReader("", "vending machine with assorted candies and treats", strArray13, "");
        edu.cmu.tartan.item.ItemDiamond itemDiamond19 = new edu.cmu.tartan.item.ItemDiamond("diamond", "hi!", strArray13, "bundle of dynamite");
        java.lang.String[] strArray24 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot26 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray24, "Tartan_save_file.dat");
        java.lang.String[] strArray31 = new java.lang.String[] { "clay pot", "clay pot" };
        edu.cmu.tartan.item.ItemClayPot itemClayPot33 = new edu.cmu.tartan.item.ItemClayPot("Tartan_save_file.dat", "vending machine with assorted candies and treats", strArray31, "Tartan_save_file.dat");
        edu.cmu.tartan.item.Item[] itemArray34 = new edu.cmu.tartan.item.Item[] { itemDiamond19, itemClayPot26, itemClayPot33 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList35 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList35, itemArray34);
        edu.cmu.tartan.Player player38 = new edu.cmu.tartan.Player(roomRequiredItem4, itemList35, "Taken");
        edu.cmu.tartan.action.Action action39 = edu.cmu.tartan.action.Action.ACTION_PASS;
        edu.cmu.tartan.room.Room room40 = roomRequiredItem4.getRoomForDirection(action39);
        boolean boolean41 = roomRequiredItem4.diesOnEntry();
        edu.cmu.tartan.Player player43 = new edu.cmu.tartan.Player(roomRequiredItem4, "bundle of dynamite");
        this.assertNotNull(strArray13);
        this.assertNotNull(strArray24);
        this.assertNotNull(strArray31);
        this.assertNotNull(itemArray34);
        this.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        this.assertTrue("'" + action39 + "' != '" + edu.cmu.tartan.action.Action.ACTION_PASS + "'", action39.equals(edu.cmu.tartan.action.Action.ACTION_PASS));
        this.assertNull(room40);
        this.assertTrue("'" + boolean41 + "' != '" + false + "'", boolean41 == false);
    }

    @Test
    public void test135() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test135");
        edu.cmu.tartan.xml.XmlNgReason xmlNgReason0 = edu.cmu.tartan.xml.XmlNgReason.DUP_ID;
        this.assertTrue("'" + xmlNgReason0 + "' != '" + edu.cmu.tartan.xml.XmlNgReason.DUP_ID + "'", xmlNgReason0.equals(edu.cmu.tartan.xml.XmlNgReason.DUP_ID));
    }

    @Test
    public void test136() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test136");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_DESTROY;
        this.assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DESTROY + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_DESTROY));
    }

    @Test
    public void test137() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test137");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.UserClientThread userClientThread3 = new edu.cmu.tartan.socket.UserClientThread(socket0, iQueueHandler1, "microwave that stinks of month old popcorn");
        userClientThread3.stopSocket();
    }

    @Test
    public void test138() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test138");
        edu.cmu.tartan.xml.XmlWriterServer xmlWriterServer0 = new edu.cmu.tartan.xml.XmlWriterServer();
    }

    @Test
    public void test139() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test139");
        java.lang.String[] strArray2 = null;
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine4 = new edu.cmu.tartan.item.ItemVendingMachine("lock", "Taken", strArray2, "Floor 3 Button");
    }

    @Test
    public void test140() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test140");
        edu.cmu.tartan.client.ClientInterface.NetworkModeCommand networkModeCommand0 = edu.cmu.tartan.client.ClientInterface.NetworkModeCommand.REGISTER;
        this.assertTrue("'" + networkModeCommand0 + "' != '" + edu.cmu.tartan.client.ClientInterface.NetworkModeCommand.REGISTER + "'", networkModeCommand0.equals(edu.cmu.tartan.client.ClientInterface.NetworkModeCommand.REGISTER));
    }

    @Disabled
    @Test
    public void test141() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test141");
        edu.cmu.tartan.GameInterface gameInterface0 = edu.cmu.tartan.GameInterface.getInterface();
        gameInterface0.print("I don't understand that.\n");
        java.lang.String str4 = gameInterface0.getCommand("");
        this.assertNotNull(gameInterface0);
        this.assertTrue("'" + str4 + "' != '" + "" + "'", str4.equals(""));
    }
}

