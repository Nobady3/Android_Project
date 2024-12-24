package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CustomerAdapter;
import com.example.myapplication.db.CustomerDbHelper;
import com.example.myapplication.entity.CustomerInfo;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private CustomerDbHelper customerDbHelper;

    private static final int ADD_CUSTOMER_REQUEST_CODE = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView and Database Helper
        recyclerView = view.findViewById(R.id.recyclerView);
        customerDbHelper = CustomerDbHelper.getInstance(getContext());

        // Set up RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get all customer data from the database
        List<CustomerInfo> customerList = customerDbHelper.getAllCustomers();

        // Set up the adapter
        adapter = new CustomerAdapter(getContext(), customerList);
        recyclerView.setAdapter(adapter);

        Button btnReflashCustomer=view.findViewById(R.id.btn_reflash_customer);
        btnReflashCustomer.setOnClickListener(v -> {
            List<CustomerInfo> updatedCustomerList = customerDbHelper.getAllCustomers();
            adapter.updateCustomerList(updatedCustomerList); // 通知适配器更新数据
        });

        // Set up the "Add Customer" button
        Button btnAddCustomer = view.findViewById(R.id.btn_add_customer);
        btnAddCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), com.example.myapplication.fragment.AddCustomerActivity.class);
            startActivityForResult(intent, ADD_CUSTOMER_REQUEST_CODE);  // 启动 AddCustomerActivity 并传递请求码
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CUSTOMER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 如果成功返回，重新加载客户列表
            List<CustomerInfo> updatedCustomerList = customerDbHelper.getAllCustomers();
            adapter.updateCustomerList(updatedCustomerList); // 通知适配器更新数据
        }
    }
}
