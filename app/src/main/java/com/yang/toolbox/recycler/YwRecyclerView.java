package com.yang.toolbox.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yang.toolbox.R;
import com.yang.toolbox.util.L;

/**
 * Created by YangWei
 * on 2016/3/28.
 */
public class YwRecyclerView extends RecyclerView {
    public static final int WRAP_CONTENT = -1;
    private RecyclerView.LayoutManager mLayoutManager;
    private int SPAN_COUNT = 1;

    public enum LayoutMode {
        VERTICAL_LIST, HORIZONTAL_LIST, VERTICAL_GRID, HORIZONTAL_GRID, STAGGERED_GRID;
    }


    public YwRecyclerView(Context context) {
        super(context);
        initDefaltLayoutManager(context, LayoutMode.VERTICAL_LIST);
    }

    public YwRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDefaltLayoutManager(context, LayoutMode.VERTICAL_LIST);
    }

    public YwRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefaltLayoutManager(context, LayoutMode.VERTICAL_LIST);
    }


    private void initDefaltLayoutManager(Context context, LayoutMode mode) {
        setLayoutManager(context, mode);

    }

    /**
     * 设置为垂直List
     *
     * @param context 上下文
     */
    public void setListHorizontalManager(Context context) {
        setLayoutManager(context, LayoutMode.HORIZONTAL_LIST);
    }

    /**
     * 设置为GridLayout形式
     *
     * @param context   上下文
     * @param spanCount 每行个数
     * @param vertical  是否垂直
     */
    public void setGridLayoutManager(Context context, int spanCount, boolean vertical) {
        this.SPAN_COUNT = spanCount;
        if (vertical)
            setLayoutManager(context, LayoutMode.VERTICAL_GRID);
        else
            setLayoutManager(context, LayoutMode.HORIZONTAL_GRID);
    }

    /**
     * 设置为瀑布流形式
     *
     * @param context   上下文
     * @param spanCount 每行个数
     */
    public void setStaggeredGridLayoutManager(Context context, int spanCount) {
        this.SPAN_COUNT = spanCount;
        setLayoutManager(context, LayoutMode.STAGGERED_GRID);
    }

    private void setLayoutManager(Context context, LayoutMode mode) {
        mLayoutManager = null;
        switch (mode) {
            case VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager = new GridLayoutManager(context, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(context, SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        setLayoutManager(mLayoutManager);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        this.mLayoutManager = layout;
        super.setLayoutManager(this.mLayoutManager);
    }

    public void setDivider() {
        setDivider(android.R.drawable.divider_horizontal_bright, 1);
    }

    /**
     * set list divider
     *
     * @param dividerRes divider resource
     */
    public void setDivider(int dividerRes) {
        setDivider(dividerRes, WRAP_CONTENT);
    }

    /**
     * set list divider
     *
     * @param dividerRes    divider resource
     * @param dividerHeight divider height
     */
    public void setDivider(int dividerRes, int dividerHeight) {
        Drawable drawable = getResources().getDrawable(dividerRes);
        setDivider(drawable, dividerHeight);
    }

    /**
     * set list divider
     *
     * @param drawable      drawable
     * @param dividerHeight divider height
     */
    public void setDivider(final Drawable drawable, final int dividerHeight) {
        if (null == drawable) {
            throw new NullPointerException("drawable resource is null");
        }
        addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom;
                    if (dividerHeight == WRAP_CONTENT) {
                        bottom = top + drawable.getIntrinsicHeight();
                    } else {
                        if (dividerHeight < 0) {
                            bottom = top;
                        } else {
                            bottom = top + dividerHeight;
                        }

                    }

                    drawable.setBounds(left, top, right, bottom);
                    drawable.draw(c);
                }
            }
        });
    }


    // RecyclerView.SCROLL_STATE_IDLE 不滚动

    private boolean loadingMore = true;//是否需要加载更多
    private boolean isLastItem = false;
    private int mVisibleItemCount, mTotalItemCount, mFirstVisibleItemPosition;
    private SwipeRefreshLayout swipeRefreshLayout;

    public void enableLoadMore(final RvListenerInf.RvLoadMore loadListener, SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        enableLoadMore(loadListener);
    }

    public void enableLoadMore(final RvListenerInf.RvLoadMore loadListener) {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (loadListener != null && isLastItem && loadingMore && newState == recyclerView.SCROLL_STATE_IDLE) {//当滑动停止时加载更多数据
                    L.e("loadingMore");
                    loadingMore = false;//防止多次加载
                    loadListener.loadMore();
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
                //mFirstVisibleItemPosition：当前能看见的第一个列表项ID（从0开始）
                //mVisibleItemCount：当前能看见的列表项个数（小半个也算）
                //mTotalItemCount：列表项共数
                mVisibleItemCount = mLayoutManager.getChildCount();
                mTotalItemCount = mLayoutManager.getItemCount();
                L.e("Last:" + getLastVisiblePosition() + ",mTotal:" + mTotalItemCount);
                //当最后一条数据完全显示时可以加载更多

                if (getLastVisiblePosition() + 1 == mTotalItemCount) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });
    }

    /**
     * 设置是否需要加载更多
     *
     * @param loadingMore
     */
    public void needLoadMore(boolean loadingMore) {
        this.loadingMore = loadingMore;
    }

    /**
     * 获取第一条展示的位置
     *
     * @return
     */
    private int getFirstVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获取完全显示的最好一条数据的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastCompletelyVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }
}
