package com.zy.yaoproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.zy.yaoproject.R;
import com.zy.yaoproject.bean.LogisticsBean;
import com.zy.yaoproject.utils.DateUtils;

/**
 * Created by muzi on 2018/4/19.
 * 727784430@qq.com
 */
public class CardStackAdapter extends StackAdapter<LogisticsBean.DataBean.ListBean.NeeadBeanBean> {

    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public CardStackAdapter(Context context, OnClick onClick) {
        super(context);
        this.onClick = onClick;
    }

    @Override
    public void bindView(LogisticsBean.DataBean.ListBean.NeeadBeanBean data, int position, CardStackView.ViewHolder holder) {
        MyViewHolder h = (MyViewHolder) holder;
        h.onBind(data, position);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_logistics, parent, false);
        return new MyViewHolder(view, onClick);
    }

    static class MyViewHolder extends CardStackView.ViewHolder {

        private View containerCard, containerContent;
        private TextView textTime, textState, textName, textNum;
        private Button btnDelivery, btnDelete;
        private OnClick onClick;

        public MyViewHolder(View view, OnClick onClick) {
            super(view);
            this.onClick = onClick;
            containerCard = view.findViewById(R.id.card_container);
            containerContent = view.findViewById(R.id.content_container);
            textTime = view.findViewById(R.id.text_time);
            textState = view.findViewById(R.id.text_state);
            textName = view.findViewById(R.id.text_name);
            textNum = view.findViewById(R.id.text_num);
            btnDelivery = view.findViewById(R.id.btn_delivery);
            btnDelete = view.findViewById(R.id.btn_delete);
        }

        @Override
        public void onItemExpand(boolean b) {
            containerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(LogisticsBean.DataBean.ListBean.NeeadBeanBean item, int position) {
            String time = DateUtils.stampToDate(item.getDate());
            textTime.setText(time);

            switch (item.getPeisongFlag()) {
                case "0":
                    //更改配送状态
                    textState.setText("待配送");
                    containerCard.setBackgroundResource(R.drawable.shape_rectangle_with_radius_0);

                    //更改配送按钮状态
                    btnDelivery.setText("配送");
                    btnDelivery.setEnabled(true);
                    btnDelivery.setBackgroundResource(R.drawable.btn_bg_delivery);
                    break;
                case "1":
                    //更改配送状态
                    textState.setText("已配送");
                    containerCard.setBackgroundResource(R.drawable.shape_rectangle_with_radius_1);

                    //更改配送按钮状态
                    btnDelivery.setText("已配送");
                    btnDelivery.setEnabled(false);
                    btnDelivery.setBackgroundResource(R.drawable.btn_bg_complete);
                    break;
            }
            textName.setText(item.getName());
            textNum.setText(item.getNumber() + "/" + item.getUnit());

            btnDelivery.setOnClickListener(v -> {
                if (onClick != null) {
                    onClick.onDelivery(position);
                }
            });
            btnDelete.setOnClickListener(v -> {
                if (onClick != null) {
                    onClick.onDelete(position);
                }
            });
        }
    }


    public interface OnClick {
        void onDelete(int position);

        void onDelivery(int position);
    }

}
