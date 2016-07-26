package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.help;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;


/**
 * Created by Nazanin on 26/07/2016.
 */
public class Help extends Fragment {
        private ViewGroup view;
        private TextView temp;
        private Button memberButton;
        private ViewGroup container;

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

            view = (ViewGroup) inflater.inflate(R.layout.help, container, false);

            Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


            setExpandButtonsFunction(R.id.help_app_description_expand_layout, R.id.help_app_description_expand_button, R.id.help_app_description_layout);
            setExpandButtonsFunction(R.id.help_start_the_app_expand_layout, R.id.help_start_the_app_expand_button, R.id.help_start_the_app_layout);
            setExpandButtonsFunction(R.id.help_differenet_types_expand_layout, R.id.help_differenet_types_expand_button, R.id.help_differenet_types_layout);
            setExpandButtonsFunction(R.id.help_user_and_admin_expand_layout, R.id.help_user_and_admin_expand_button, R.id.help_user_and_admin_layout);


            return view;
        }

        private void setExpandButtonsFunction(int layoutId, final int imageViewId, final int layoutBelowId) {

            view.findViewById(layoutId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View layoutBelow = view.findViewById(layoutBelowId);

                    if (layoutBelow.getVisibility() == View.VISIBLE) {
                        layoutBelow.setVisibility(View.GONE);
                        ((ImageView) view.findViewById(imageViewId)).setImageResource(R.drawable.ic_expand_more_white_24dp);
                    } else {
                        layoutBelow.setVisibility(View.VISIBLE);
                        ((ImageView) view.findViewById(imageViewId)).setImageResource(R.drawable.ic_expand_less_white_24dp);
                    }
                }
            });

        }


    }
