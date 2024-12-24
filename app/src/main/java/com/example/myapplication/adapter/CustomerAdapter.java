package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.CustomerDbHelper;
import com.example.myapplication.entity.CustomerInfo;
import com.example.myapplication.fragment.ChangeCustomerActivity;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private List<CustomerInfo> customerList;
    private CustomerDbHelper dbHelper;

    public CustomerAdapter(Context context, List<CustomerInfo> customerList) {
        this.context = context;
        this.customerList = customerList;
        this.dbHelper = CustomerDbHelper.getInstance(context);
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }
    // 更新数据的方法
    public void updateCustomerList(List<CustomerInfo> newCustomerList) {
        this.customerList = newCustomerList;
        notifyDataSetChanged(); // 通知适配器更新界面
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        CustomerInfo customer = customerList.get(position);

        // Display customer information
        holder.tvCustomerId.setText(String.format("%03d", customer.getCustomerId()));
        holder.tvCustomerName.setText(String.valueOf(customer.getAccountNumber()));
        holder.tvFullName.setText(customer.getFullName());

        // Modify button click listener
        holder.btnModify.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChangeCustomerActivity.class);
            intent.putExtra("customer_id", customer.getCustomerId()); // 传递客户的ID
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            // 显示确认删除对话框
            new AlertDialog.Builder(context)
                    .setTitle("温馨提示")
                    .setMessage("确定要删除该客户吗?")
                    .setNegativeButton("取消", (dialog, which) -> {
                        // 取消删除操作，不做任何处理
                    })
                    .setPositiveButton("确认", (dialog, which) -> {
                        // 执行删除操作
                        dbHelper.deleteCustomer(customer.getCustomerId()); // 删除数据库中的客户信息

                        // 确保删除了数据列表中的项
                        customerList.remove(position); // 从列表中删除

                        // 通知适配器更新 UI
                        notifyItemRemoved(position); // 通知适配器移除特定位置的项
                        notifyItemRangeChanged(position, getItemCount()); // 确保更新剩余的项

                        // 显示删除成功的提示
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    })
                    .show(); // 显示对话框
        });

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView tvCustomerId, tvCustomerName,tvFullName;
        Button btnModify, btnDelete;
        CardView cardView;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            tvCustomerId = itemView.findViewById(R.id.tvCustomerId);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvCustomerName=itemView.findViewById(R.id.tvCustomerName);
            btnModify = itemView.findViewById(R.id.btnModify);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
