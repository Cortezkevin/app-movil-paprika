package com.example.paprika;

import android.nfc.Tag;

import androidx.fragment.app.Fragment;

public interface NavigationHost {

    void navigateTo(Fragment fragment, boolean addToBackStack);

    void hideShowFragment(Fragment oldFragment, Fragment newFragment, String tag);
}
