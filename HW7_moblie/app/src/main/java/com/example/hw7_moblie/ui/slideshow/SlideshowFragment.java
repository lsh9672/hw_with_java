package com.example.hw7_moblie.ui.slideshow;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.hw7_moblie.MainActivity;
import com.example.hw7_moblie.R;
import com.google.android.material.navigation.NavigationView;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    View dialogview;
    ImageView nav_header_image1;
    TextView nav_header_tx1,nav_header_tx2;
    EditText name_input, email_input;
    int index_temp=0;
    String login_check;
    MenuItem menu;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0); // headerView를 가져옴
        menu = navigationView.getMenu().findItem(R.id.nav_slideshow);
        nav_header_tx1 = header.findViewById(R.id.nav_header_tx1);
        nav_header_tx2 = header.findViewById(R.id.nav_header_tx2);
        nav_header_image1 = header.findViewById(R.id.nav_header_image);
        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

        login_check = menu.getTitle().toString();

        if (login_check.equals("Logout")) {
            nav_header_image1.setImageResource(R.mipmap.ic_launcher_round);
            nav_header_tx1.setText(getString(R.string.nav_header_title));
            nav_header_tx2.setText(getString(R.string.nav_header_subtitle));
            menu.setTitle("Login");
            toolbar.setTitle("Login");
            Toast.makeText(getContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
        }
        else if (login_check.equals("Login")) {
            toolbar.setTitle("Login");
            final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
            dialogview = (View) View.inflate(getActivity(), R.layout.dialogview, null);
            name_input = dialogview.findViewById(R.id.name_input);
            email_input = dialogview.findViewById(R.id.email_input);

            dlg.setIcon(R.mipmap.ic_launcher);
            dlg.setView(dialogview);
            dlg.setTitle("정보 입력");

            final String[] dlgArray = new String[]{"강아지", "고양이", "말"};

            dlg.setSingleChoiceItems(dlgArray, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    index_temp = which;

                }
            });

            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    //item.setTitle(getString(R.string.menu_logout));
                    if (index_temp == 0) {
                        nav_header_image1.setImageResource(R.drawable.puppy);
                    } else if (index_temp == 1) {
                        nav_header_image1.setImageResource(R.drawable.cat);
                    } else if (index_temp == 2) {
                        nav_header_image1.setImageResource(R.drawable.horse);
                    }
                    nav_header_tx1.setText(name_input.getText().toString());
                    nav_header_tx2.setText(email_input.getText().toString());
                    menu.setTitle("Logout");
                    toolbar.setTitle("Logout");
                }
            });
            dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nav_header_image1.setImageResource(R.mipmap.ic_launcher_round);
                    nav_header_tx1.setText(getString(R.string.nav_header_title));
                    nav_header_tx2.setText(getString(R.string.nav_header_subtitle));
                    Toast.makeText(getContext(), "취소됨", Toast.LENGTH_SHORT).show();
                    menu.setTitle("Login");
                    toolbar.setTitle("Login");
                }
            });

            dlg.show();

        }
        return root;
    }
}