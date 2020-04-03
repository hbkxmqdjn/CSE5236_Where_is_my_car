package com.bignerdranch.android.findmycar.ui;
import com.bignerdranch.android.findmycar.R;
import androidx.fragment.app.Fragment;

public class AccountActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new AccountFragment();
    }


}