package com.example.a23936.shoppingmall;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 23936 on 2019/4/16.
 */

public class Classification extends Fragment implements View.OnClickListener{

    private TextView text_new;
    private TextView text_phone;
    private TextView text_tv;
    private TextView text_computer;

    private NewFragment newFragment = new NewFragment();
    private PhoneFragmnet phoneFragmnet = new PhoneFragmnet();
    private TVFragment tvFragment = new TVFragment();
    private ComputerFragment computerFragment = new ComputerFragment();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.classification_layout,null);

        initView(view);

        initFragment();
        return view;
    }

    private void initView(View view){
        text_new = view.findViewById(R.id.classifition_text_new);
        text_phone = view.findViewById(R.id.classifition_text_phone);
        text_tv = view.findViewById(R.id.classifition_text_tv);
        text_computer = view.findViewById(R.id.classifition_text_computer);

        text_new.setOnClickListener(this);
        text_computer.setOnClickListener(this);
        text_tv.setOnClickListener(this);
        text_phone.setOnClickListener(this);

    }

    private void initFragment(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (!newFragment.isAdded()){
            fragmentTransaction.add(R.id.classifition_fragment,newFragment);
            fragmentTransaction.hide(newFragment);
        }
        if (!phoneFragmnet.isAdded()){
            fragmentTransaction.add(R.id.classifition_fragment,phoneFragmnet);
            fragmentTransaction.hide(phoneFragmnet);
        }
        if (!tvFragment.isAdded()){
            fragmentTransaction.add(R.id.classifition_fragment,tvFragment);
            fragmentTransaction.hide(tvFragment);
        }
        if (!computerFragment.isAdded()){
            fragmentTransaction.add(R.id.classifition_fragment,computerFragment);
            fragmentTransaction.hide(computerFragment);
        }

        hideFragment(fragmentTransaction);
        fragmentTransaction.show(newFragment);
        fragmentTransaction.commit();

    }


    private void hideFragment(FragmentTransaction fragmentTransaction){
        fragmentTransaction.hide(newFragment);
        fragmentTransaction.hide(phoneFragmnet);
        fragmentTransaction.hide(tvFragment);
        fragmentTransaction.hide(computerFragment);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.classifition_text_new:
                clickTab(newFragment);
                break;
            case R.id.classifition_text_phone:
                clickTab(phoneFragmnet);
                break;
            case R.id.classifition_text_tv:
                clickTab(tvFragment);
                break;
            case R.id.classifition_text_computer:
                clickTab(computerFragment);
                break;
            default:
                break;
        }
    }

    private void clickTab(Fragment tabFragment){
        clearSelect();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        hideFragment(fragmentTransaction);

        fragmentTransaction.show(tabFragment);
        fragmentTransaction.commit();

        changeTabStyle(tabFragment);


    }

    public void clearSelect(){
        if (!newFragment.isHidden()){
            text_new.setTextColor(Color.parseColor("#393939"));
        }
        if (!phoneFragmnet.isHidden()){
            text_phone.setTextColor(Color.parseColor("#393939"));
        }
        if (!tvFragment.isHidden()){
            text_tv.setTextColor(Color.parseColor("#393939"));
        }
        if (!computerFragment.isHidden()){
            text_computer.setTextColor(Color.parseColor("#393939"));
        }
    }


    public void changeTabStyle(Fragment tabFragment){
        if (tabFragment instanceof NewFragment){
            text_new.setTextColor(Color.parseColor("#0da0f2"));
        }
        if (tabFragment instanceof PhoneFragmnet){
            text_phone.setTextColor(Color.parseColor("#0da0f2"));
        }
        if (tabFragment instanceof TVFragment){
            text_tv.setTextColor(Color.parseColor("#0da0f2"));
        }
        if (tabFragment instanceof ComputerFragment){
            text_computer.setTextColor(Color.parseColor("#0da0f2"));
        }
    }

}
