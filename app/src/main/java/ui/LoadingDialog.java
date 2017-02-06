package ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.xiao.cui.R;

/**
 * Created by leo on 16/5/8.
 * Loading Dialog
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_loading);
        setCancelable(false);
    }
}
