package com.eservia.overscroll.adapters;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.eservia.overscroll.HorizontalOverScrollBounceEffectDecorator;
import com.eservia.overscroll.VerticalOverScrollBounceEffectDecorator;

import java.util.List;

/**
 * @see HorizontalOverScrollBounceEffectDecorator
 * @see VerticalOverScrollBounceEffectDecorator
 */
public class RecyclerViewOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {

    /**
     * A delegation of the adapter implementation of this view that should provide the processing
     * of {@link #isInAbsoluteStart()} and {@link #isInAbsoluteEnd()}. Essentially needed simply
     * because the implementation depends on the layout manager implementation being used.
     */
    protected interface Impl {
        boolean isInAbsoluteStart();
        boolean isInAbsoluteEnd();
    }

    protected final RecyclerView mRecyclerView;
    protected final Impl mImpl;

    protected boolean mIsItemTouchInEffect = false;

    public RecyclerViewOverScrollDecorAdapter(RecyclerView recyclerView) {

        mRecyclerView = recyclerView;

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager ||
            layoutManager instanceof StaggeredGridLayoutManager)
        {
            final int orientation =
                    (layoutManager instanceof LinearLayoutManager
                        ? ((LinearLayoutManager) layoutManager).getOrientation()
                        : ((StaggeredGridLayoutManager) layoutManager).getOrientation());

            if (orientation == LinearLayoutManager.HORIZONTAL) {
                mImpl = new ImplHorizLayout();
            } else {
                mImpl = new ImplVerticalLayout();
            }
        }
        else
        {
            throw new IllegalArgumentException("Recycler views with custom layout managers are not supported by this adapter out of the box." +
                    "Try implementing and providing an explicit 'impl' parameter to the other c'tors, or otherwise create a custom adapter subclass of your own.");
        }
    }

    public RecyclerViewOverScrollDecorAdapter(RecyclerView recyclerView, Impl impl) {
        mRecyclerView = recyclerView;
        mImpl = impl;
    }

    public RecyclerViewOverScrollDecorAdapter(RecyclerView recyclerView, ItemTouchHelper.Callback itemTouchHelperCallback) {
        this(recyclerView);
        setUpTouchHelperCallback(itemTouchHelperCallback);
    }

    public RecyclerViewOverScrollDecorAdapter(RecyclerView recyclerView, Impl impl, ItemTouchHelper.Callback itemTouchHelperCallback) {
        this(recyclerView, impl);
        setUpTouchHelperCallback(itemTouchHelperCallback);
    }

    protected void setUpTouchHelperCallback(final ItemTouchHelper.Callback itemTouchHelperCallback) {
        new ItemTouchHelper(new ItemTouchHelperCallbackWrapper(itemTouchHelperCallback) {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                mIsItemTouchInEffect = actionState != 0;
                super.onSelectedChanged(viewHolder, actionState);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public View getView() {
        return mRecyclerView;
    }

    @Override
    public boolean isInAbsoluteStart() {
        return !mIsItemTouchInEffect && mImpl.isInAbsoluteStart();
    }

    @Override
    public boolean isInAbsoluteEnd() {
        return !mIsItemTouchInEffect && mImpl.isInAbsoluteEnd();
    }

    protected class ImplHorizLayout implements Impl {

        @Override
        public boolean isInAbsoluteStart() {
            return !mRecyclerView.canScrollHorizontally(-1);
        }

        @Override
        public boolean isInAbsoluteEnd() {
            return !mRecyclerView.canScrollHorizontally(1);
        }
    }

    protected class ImplVerticalLayout implements Impl {

        @Override
        public boolean isInAbsoluteStart() {
            return !mRecyclerView.canScrollVertically(-1);
        }

        @Override
        public boolean isInAbsoluteEnd() {
            return !mRecyclerView.canScrollVertically(1);
        }
    }

    private static class ItemTouchHelperCallbackWrapper extends ItemTouchHelper.Callback {

        final ItemTouchHelper.Callback mCallback;

        private ItemTouchHelperCallbackWrapper(ItemTouchHelper.Callback callback) {
            mCallback = callback;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return mCallback.getMovementFlags(recyclerView, viewHolder);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return mCallback.onMove(recyclerView, viewHolder, target);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mCallback.onSwiped(viewHolder, direction);
        }

        @Override
        public int convertToAbsoluteDirection(int flags, int layoutDirection) {
            return mCallback.convertToAbsoluteDirection(flags, layoutDirection);
        }

        @Override
        public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
            return mCallback.canDropOver(recyclerView, current, target);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return mCallback.isLongPressDragEnabled();
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return mCallback.isItemViewSwipeEnabled();
        }

        @Override
        public int getBoundingBoxMargin() {
            return mCallback.getBoundingBoxMargin();
        }

        @Override
        public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return mCallback.getSwipeThreshold(viewHolder);
        }

        @Override
        public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return mCallback.getMoveThreshold(viewHolder);
        }

        @Override
        public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
            return mCallback.chooseDropTarget(selected, dropTargets, curX, curY);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            mCallback.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
            mCallback.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            mCallback.clearView(recyclerView, viewHolder);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            mCallback.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            mCallback.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
            return mCallback.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
        }

        @Override
        public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
            return mCallback.interpolateOutOfBoundsScroll(recyclerView, viewSize, viewSizeOutOfBounds, totalSize, msSinceStartScroll);
        }
    }
}
