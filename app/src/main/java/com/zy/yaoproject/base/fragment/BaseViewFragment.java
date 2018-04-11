package com.zy.yaoproject.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.zy.yaoproject.R;
import com.zy.yaoproject.base.inter.IBaseView;
import com.zy.yaoproject.ui.LoginActivity;
import com.zy.yaoproject.utils.ToastUtils;
import com.zy.yaoproject.widget.LoadingDialog;


/**
 * Created by muzi on 2017/12/27.
 * 727784430@qq.com
 */

public abstract class BaseViewFragment extends BaseInitFragment implements IBaseView {

    protected SwipeRefreshLayout refreshLayout;

    protected LoadingDialog loadingDialog;

    /**
     * 初始化刷新
     *
     * @param refreshLayout
     * @param onRefreshListener
     */
    @Override
    public void initRefresh(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.refreshLayout = refreshLayout;
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.themeColor));
        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        refreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 取消刷新
     */
    @Override
    public void refreshFinish() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show();
    }

    /**
     * 隐藏进度条
     */
    @Override
    public void hideProgress() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(getContext(), msg);
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public void startLogin() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

}
