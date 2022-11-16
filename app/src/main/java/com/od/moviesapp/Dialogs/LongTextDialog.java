package com.od.moviesapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import androidx.annotation.NonNull;
import com.od.moviesapp.databinding.LayoutLongTextBinding;

public class LongTextDialog extends Dialog{
    LayoutLongTextBinding binding;

    public LongTextDialog(@NonNull final Context context, String desc) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = LayoutLongTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textViewMovieDescLong.setText(desc);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}