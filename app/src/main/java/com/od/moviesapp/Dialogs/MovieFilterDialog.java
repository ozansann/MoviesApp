package com.od.moviesapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import com.od.moviesapp.Fragments.MainFragment;
import com.od.moviesapp.R;
import com.od.moviesapp.databinding.MovieFilterDialogBinding;
import java.util.ArrayList;
import java.util.Calendar;

public class MovieFilterDialog extends Dialog{
    MovieFilterDialogBinding binding;
    ArrayList<String> yearsList;
    String movieName;
    Integer movieYear,selectedYearIndex;

    public MovieFilterDialog(@NonNull final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = MovieFilterDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edtMovieName.requestFocus();
        initializeSpinner();
        setOnClickListeners();
    }

    public void initializeSpinner(){
        yearsList = new ArrayList<>();
        int current_year = Calendar.getInstance().get(Calendar.YEAR);
        int count = 100;
        yearsList.add(getContext().getString(R.string.select_movie_year));
        for (int i = 0; i < count; i++) {
            yearsList.add(Integer.toString(current_year - i));
        }
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearsList);
        binding.spinnerYear.setAdapter(yearsAdapter);
    }

    public void setOnClickListeners(){
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieName = binding.edtMovieName.getEditText().getText().toString().trim();
                selectedYearIndex = binding.spinnerYear.getSelectedItemPosition();
                movieYear = selectedYearIndex == 0 ? 0 : Integer.valueOf(yearsList.get(selectedYearIndex));
                MainFragment.setSearchFilters(movieName,movieYear);
                dismiss();
            }
        });
    }
}