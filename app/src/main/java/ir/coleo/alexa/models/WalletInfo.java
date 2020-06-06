package ir.coleo.alexa.models;

import android.content.Intent;

public class WalletInfo {

    Intent intent;
    String text;
    int icon;

    public WalletInfo(Intent intent, String title, int icon) {
        this.intent = intent;
        this.text = title;
        this.icon = icon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
