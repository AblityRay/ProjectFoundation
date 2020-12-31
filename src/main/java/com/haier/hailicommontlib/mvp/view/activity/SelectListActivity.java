package com.haier.hailicommontlib.mvp.view.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haier.hailicommontlib.R;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp.model.utils.WindowsUtil;
import com.haier.hailicommontlib.mvp.view.custom.NoSlidingViewPager;
import com.haier.hailicommontlib.mvp.view.fragment.BaseFragment;
import com.haier.hailicommontlib.mvp_api.moudle.bean.SelectPageBeen;
import com.haier.hailicommontlib.mvp_api.presenter.IPresenter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Viewpager +fragment 切换基类 并且Fragment里边为列表形式数据
 */
public abstract class SelectListActivity extends BaseActivity {

    private IPresenter presenter;
    public LinearLayout llyBaseContainer;
    public LinearLayout llyTopSelect;
    public NoSlidingViewPager vpFragment;






    public ArrayList<BaseFragment> fragments;
    ArrayList<SelectPageBeen> selectTitles;


    public ArrayList<Drawable> selectTrue = new ArrayList<>();

    public ArrayList<Drawable> selectFalse = new ArrayList<>();


    private Resources resources;

    //选择器是否有图片
    public abstract boolean isHaveImage();

    //选择器中的图片
    public abstract ArrayList<Drawable> setSelectDrawableTrue();

    public abstract ArrayList<Drawable> setSelectDrawableFalse();

    //选择器距屏幕左右间距
    private final int SELECT_VIEW_PADDING = 30;
    private String TAG = "SELECT_LIST";
    private HorizontalScrollView horizontalScrollView;
    private int ScreenWidth; //屏幕宽度

//    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initView() {
        resources = getResources();
        //屏幕宽度
        ScreenWidth = WindowsUtil.WindowsWidth(this);
        /**
         * 图片
         */
        if (isHaveImage()) {
            selectTrue.addAll(setSelectDrawableTrue());
            selectFalse.addAll(setSelectDrawableFalse());
        }
        vpFragment = findViewById(R.id.vp_fragment);

        /**
         * 添加选择区域
         */

        llyBaseContainer = findViewById(R.id.lly_base_container);
        //选择ViewGroup  显示选择条目
        View view = View.inflate(this, R.layout.select_title_item_not_background, null);
        horizontalScrollView = view.findViewById(R.id.hsv);

        View viewTop = view.findViewById(R.id.view_top);
        llyTopSelect = view.findViewById(R.id.lly_top_select);
        ViewGroup.LayoutParams layoutParams;
        if (isHaveProjection) {
            llyTopSelect.setBackground(getResources().getDrawable(R.drawable.cardview_background));
//            llyTopSelect.setPadding(SELECT_VIEW_PADDING / 2, 0, SELECT_VIEW_PADDING / 2, 0);

            layoutParams = new LinearLayout.LayoutParams(WindowsUtil.WindowsWidth(this), WindowsUtil.convertDpToPixel(this, 80));
        } else {
            layoutParams = new LinearLayout.LayoutParams(WindowsUtil.WindowsWidth(this), WindowsUtil.convertDpToPixel(this, 50));
        }

//        view.setForegroundGravity(Gravity.CENTER_VERTICAL);
        if (TITLE_TOP == getTitleTopOrBottom()) {
            viewTop.setVisibility(View.VISIBLE);
            llyBaseContainer.addView(view, 0, layoutParams);
        } else {
            viewTop.setVisibility(View.GONE);
            llyBaseContainer.addView(view, 1, layoutParams);
        }
        dataStute(vpFragment, -1);
        selectTitles = getSelectTitle();

        if (selectTitles != null && selectTitles.size() > 0) {
            fragments = getFragment();
            if (fragments.size() > 0) {
                vpFragment.setOffscreenPageLimit(fragments.size());
            }
        }

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        vpFragment.setAdapter(myFragmentAdapter);
        vpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                refreshTitleList(selectTitles, position);
                LogUtil.I(TAG, position + "==position");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        refreshTitleList(selectTitles, 0);
    }
    MyFragmentAdapter myFragmentAdapter;

    class MyFragmentAdapter extends FragmentPagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private int titleSize = 0;

    /**
     * 设置标题大小   直接设置UI图上标注的px值（本类内部会转换成dp，并重新计算他的px值）（基于2倍dp的Px）
     *
     * @param sizePx 设置头部字体大小 默认11dp
     */
    public void setTitleTextSize(int sizePx) {
        this.titleSize = sizePx;
        if (selectTitles != null)
            refreshTitleList(selectTitles, 0);
    }

    private boolean isHaveProjection = false;

    /**
     * 是否展示头部背景阴影
     *
     * @param isHaveProjection true 显示
     */
    public void setIsHaveProjection(boolean isHaveProjection) {
        this.isHaveProjection = isHaveProjection;
    }


    //标题在界面顶部
    public static final int TITLE_TOP = 1;
    //标题在界面底部
    public static final int TITLE_BOTTOM = 2;

    //设置标题位置
    public abstract int getTitleTopOrBottom();

    /**
     * 刷新头部
     *
     * @param titleList 头部列表
     */
    public void refreshTitleList(ArrayList<SelectPageBeen> titleList, int position) {
        if (llyTopSelect == null || titleList == null) {
            return;
        }
        if (vpFragment != null) {
            if (myFragmentAdapter != null && myFragmentAdapter.getCount() > position) {
                vpFragment.setCurrentItem(position);
            }
        }
        selectTitles = titleList;
        llyTopSelect.removeAllViews();
        LinearLayout.LayoutParams layoutParams = null;//条目宽度
        //初始化 头部Title
        for (int i = 0; i < titleList.size(); i++) {
            //条目背景选择器（阴影效果）
            View viewBackGround = View.inflate(SelectListActivity.this, R.layout.adapter_base_view, null);
            viewBackGround.findViewById(R.id.v_padding).setVisibility(View.GONE);
            LinearLayout llyMargin = viewBackGround.findViewById(R.id.lly_margin);
            //条目布局
            View view = View.inflate(SelectListActivity.this, R.layout.select, null);
            TextView name = view.findViewById(R.id.tv_name);
            if (titleSize != 0) {
                name.setTextSize(titleSize / 2);
            }// if (TITLE_TOP == getTitleTopOrBottom()) {
            name.setTextColor(resources.getColor(R.color.first_page_bottom_text_false));
//            ImageView imageView = view.findViewById(R.id.iv_image);

            if (isHaveImage()) {
                if (i == position) {
                    if (isHaveProjection) {
                        llyMargin.setBackground(getResources().getDrawable(R.drawable.first_laundry_item_select));
                    }
                    name.setTextColor(resources.getColor(R.color.first_page_bottom_text_true));
                    if (selectTrue.size() > i) {
                        selectTrue.get(i).setBounds(0, 0, selectTrue.get(i).getMinimumWidth(), selectTrue.get(i).getMinimumHeight());
                        name.setCompoundDrawables(null, selectTrue.get(i), null, null);
                    }
                } else {
                    if (isHaveProjection) {
                        llyMargin.setBackground(null);
                    }
                    name.setTextColor(resources.getColor(R.color.first_page_bottom_text_false));
                    if (selectFalse.size() > i) {
                        selectFalse.get(i).setBounds(0, 0, selectFalse.get(i).getMinimumWidth(), selectFalse.get(i).getMinimumHeight());
                        name.setCompoundDrawables(null, selectFalse.get(i), null, null);

                    }
                }
            }

            name.setText(titleList.get(i).getContent());
            view.setOnClickListener(new SelectOnClienlinsenter(i));

            //设置条目宽度
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            if (titleList.size() <= 4) {
                layoutParams.weight = 1;
                layoutParams.width = WindowsUtil.WindowsWidth(this) / titleList.size();

            } else {
                layoutParams.width = WindowsUtil.WindowsWidth(this) / 4;
            }
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            if (isHaveProjection) {
                //设置条目内容宽度
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                if (titleList.size() == 1) {
                    layoutParams2.width = (WindowsUtil.WindowsWidth(this) - SELECT_VIEW_PADDING) / 2;
                    layoutParams2.height = layoutParams2.height - 12;
                } else if (titleList.size() <= 4) {
                    layoutParams2.width = (WindowsUtil.WindowsWidth(this) - SELECT_VIEW_PADDING) / titleList.size();
                    layoutParams2.height = layoutParams2.height - 12;
                } else {
                    layoutParams2.width = WindowsUtil.WindowsWidth(this) / 4;
                    layoutParams2.height = layoutParams2.height - 12;
                }
                llyMargin.addView(view, layoutParams2);

                //设置阴影布局的参数
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) llyMargin.getLayoutParams();
                layoutParams3.setMargins(10, 0, 10, 12);

                llyMargin.setLayoutParams(layoutParams3);


                llyMargin.setGravity(Gravity.CENTER);
                llyTopSelect.addView(viewBackGround, layoutParams);
            } else {
                llyTopSelect.addView(view, layoutParams);
            }
        }
        llyTopSelect.getWidth();
        //滚动标题
        //使用选中的条目左边框距离scrollView 起点距离，减去（屏幕一半的大小减去一个条目一半的大小）————居中显示
        if (layoutParams != null) {
            int itemWidth = layoutParams.width;
            int s = ScreenWidth / 2 - itemWidth / 2;
            int left = itemWidth * position;
            int item_Pw = left -s ;
            horizontalScrollView.smoothScrollTo(item_Pw, 0);
        }
    }


    /**
     * 返回展示的fragment
     */
    public abstract ArrayList<BaseFragment> getFragment();

    /**
     * 返回展示的title
     */
    public abstract ArrayList<SelectPageBeen> getSelectTitle();

    @Override
    public abstract void initData();

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_list;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reload_btn) {
            refreshPage();
        }
    }


    /**
     * 头部Title列表点击事件
     */
    private class SelectOnClienlinsenter implements View.OnClickListener {
        private int i;

        private SelectOnClienlinsenter(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View v) {
//            selectPage(i);
            refreshTitleList(selectTitles, i);
        }
    }


    /**
     * 子类去实现切换页面
     *
     * @param position 页面ID
     */
    public void selectPage(int position) {
        if (vpFragment != null) {
            if (myFragmentAdapter.getCount() > position) {
                vpFragment.setCurrentItem(position);
                for (int z = 0; z < llyTopSelect.getChildCount(); z++) {
                    View childAt = llyTopSelect.getChildAt(z);
                    View view = childAt.findViewById(R.id.lly_margin);
                    TextView name = childAt.findViewById(R.id.tv_name);
                    //设置图片
                    if (isHaveImage()) {
                        if (z == position) {
                            if (isHaveProjection) {
                                view.setBackground(getResources().getDrawable(R.drawable.first_laundry_item_select));
                            }
                            name.setTextColor(resources.getColor(R.color.first_page_bottom_text_true));
                            if (selectTrue.size() > z) {
                                selectTrue.get(z).setBounds(0, 0, selectTrue.get(z).getMinimumWidth(), selectTrue.get(z).getMinimumHeight());
                                name.setCompoundDrawables(null, selectTrue.get(z), null, null);
                            }
                        } else {
                            if (isHaveProjection) {
                                view.setBackground(null);
                            }
                            name.setTextColor(resources.getColor(R.color.first_page_bottom_text_false));
                            if (selectFalse.size() > z) {
                                selectFalse.get(z).setBounds(0, 0, selectFalse.get(z).getMinimumWidth(), selectFalse.get(z).getMinimumHeight());
                                name.setCompoundDrawables(null, selectFalse.get(z), null, null);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragments != null) {
            fragments.clear();
            fragments = null;
        }
        if (selectTitles != null) {
            selectTitles.clear();
            selectTitles = null;
        }
        if (selectFalse != null) {
            selectFalse.clear();
            selectFalse = null;
        }
        if (selectTrue != null) {
            selectTrue.clear();
            selectTrue = null;
        }
    }
}
