package javafx;

public class PageFactory {

    public static Page CreateTestTopBar(IPageNavigator nav) {
        return new TestTopBar(nav);
    }

    public static Page CreateMainPage() {
        return new MainScene();
    }

    public static Page CreateSecondaryPage () {
        return new SecondaryScene();
    }

}
