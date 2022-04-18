package com.eservia.simplecalendar;

import android.content.Context;
import android.os.CountDownTimer;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.simplecalendar.adapter.DayAdapter;
import com.eservia.simplecalendar.adapter.DayAdapterItem;
import com.eservia.simplecalendar.adapter.OnDayItemClickListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SimpleCalendar extends RelativeLayout implements OnDayItemClickListener {

    private RelativeLayout rlCalendar;

    private RelativeLayout rlPreviousPage;
    private RelativeLayout rlNextPage;

    private TextView tvMonth;

    private RecyclerView rvMonth1;
    private RecyclerView rvMonth2;

    private Animation mSlideLeftHideAnimation;
    private Animation mSlideLeftShowAnimation;

    private Animation mSlideRightHideAnimation;
    private Animation mSlideRightShowAnimation;

    private DayAdapter mDayAdapter1;
    private DayAdapter mDayAdapter2;

    private SimpleCalendarListener mSimpleCalendarListener;

    private DateTime mSelectedDay;

    private List<DateTime> mAvailableDays = new ArrayList<>();

    private DateTime mVisibleDay;

    private int mVisibleMonthAfterNow = 4;

    private int mVisibleMonthBeforeNow = 2;

    private Appearance mAppearance = Appearance.WEEK;

    private boolean mCanChangeAppearance = true;

    public SimpleCalendar(Context context) {
        this(context, null);
    }

    public SimpleCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    public void onDayItemClick(DayAdapterItem adapterItem) {
        mDayAdapter1.setSelected(adapterItem);
        mDayAdapter2.setSelected(adapterItem);

        mSelectedDay = adapterItem.getDateTime();

        if (mSimpleCalendarListener != null) {
            mSimpleCalendarListener.onDaySelected(adapterItem.getDateTime());
        }
    }

    public void initCalendar(List<DateTime> availableDays) {
        mAvailableDays = availableDays;
        updateAppearance();
        initCurrentMonthName();
    }

    public void setCalendarListener(SimpleCalendarListener listener) {
        mSimpleCalendarListener = listener;
    }

    public void setVisibleMonthAfterNow(int visibleMonth) {
        mVisibleMonthAfterNow = visibleMonth;
    }

    public void setVisibleMonthBeforeNow(int visibleMonth) {
        mVisibleMonthBeforeNow = visibleMonth;
    }

    public void setSelectedColor(int colorId) {
        mDayAdapter1.setItemColorId(colorId);
        mDayAdapter2.setItemColorId(colorId);
    }

    public void setVisibleDay(DateTime day) {
        computeVisibleDay(day);
        revalidateNextPageView();
        revalidatePrevPageView();
    }

    public void selectDay(DateTime day) {
        mSelectedDay = day;

        if (day == null) {
            return;
        }

        DayAdapterItem item = mDayAdapter1.getItemByDate(day);

        if (item != null && !item.isHoliday() && !item.isEmpty()) {
            onDayItemClick(item);
        }
    }

    public boolean setAppearance(Appearance appearance) {
        if (!mCanChangeAppearance) {
            return false;
        }
        mCanChangeAppearance = false;
        startChangeAppearanceIntervalTimer();
        mAppearance = appearance;
        onAppearanceChanged();
        return true;
    }

    public Appearance getAppearance() {
        return mAppearance;
    }

    private void onAppearanceChanged() {
        runChangeAppearanceAnimation();
        updateAppearance();
    }

    private void updateAppearance() {
        initCurrentDays();
    }

    private void startChangeAppearanceIntervalTimer() {
        new ChangeAppearanceIntervalTimer(900, 900).start();
    }

    private void runChangeAppearanceAnimation() {
        AutoTransition transition = new AutoTransition();
        transition.setDuration(210);
        TransitionManager.beginDelayedTransition(rlCalendar, transition);
    }

    private void loadNextPage(List<DayAdapterItem> days) {
        boolean firstActive = firstListActive();
        if (firstActive) {
            mDayAdapter2.replaceAll(days);
        } else {
            mDayAdapter1.replaceAll(days);
        }

        rvMonth1.setEnabled(!firstActive);
        rvMonth2.setEnabled(firstActive);
        rvMonth1.setVisibility(View.VISIBLE);
        rvMonth2.setVisibility(View.VISIBLE);

        if (mSlideRightShowAnimation.hasStarted()) {
            mSlideRightShowAnimation.cancel();
        }

        if (mSlideRightHideAnimation.hasStarted()) {
            mSlideRightHideAnimation.cancel();
        }

        rvMonth1.startAnimation(firstActive ? mSlideRightHideAnimation : mSlideRightShowAnimation);
        rvMonth2.startAnimation(firstActive ? mSlideRightShowAnimation : mSlideRightHideAnimation);

        mSlideRightHideAnimation.setAnimationListener(new SlideRightAnimationListener(firstActive));
    }

    private void loadPreviousPage(List<DayAdapterItem> days) {
        boolean firstActive = firstListActive();
        if (firstActive) {
            mDayAdapter2.replaceAll(days);
        } else {
            mDayAdapter1.replaceAll(days);
        }

        rvMonth1.setEnabled(!firstActive);
        rvMonth2.setEnabled(firstActive);
        rvMonth1.setVisibility(View.VISIBLE);
        rvMonth2.setVisibility(View.VISIBLE);

        if (mSlideLeftShowAnimation.hasStarted()) {
            mSlideLeftShowAnimation.cancel();
        }

        if (mSlideLeftHideAnimation.hasStarted()) {
            mSlideLeftHideAnimation.cancel();
        }

        rvMonth1.startAnimation(firstActive ? mSlideLeftHideAnimation : mSlideLeftShowAnimation);
        rvMonth2.startAnimation(firstActive ? mSlideLeftShowAnimation : mSlideLeftHideAnimation);

        mSlideLeftHideAnimation.setAnimationListener(new SlideLeftAnimationListener(firstActive));
    }

    private void setMonthName(int indexMonth, String year) {
        String[] stringArray = getResources().getStringArray(
                R.array.activity_reservation_beauty_choose_date_month);
        if (stringArray.length > indexMonth) {
            tvMonth.setText(stringArray[indexMonth]
                    .concat(" ")
                    .concat(year));
        } else {
            tvMonth.setText("");
        }
    }

    private void loadCurrentDays(List<DayAdapterItem> days) {
        dayAdapter().replaceAll(days);
    }

    private void enablePrevMonth(boolean enabled) {
        rlPreviousPage.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    private void enableNextMonth(boolean enabled) {
        rlNextPage.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    private void onCalendarNextPageClick() {
        if (mAppearance.equals(Appearance.WEEK)) {
            showNextWeek();
        } else if (mAppearance.equals(Appearance.MONTH)) {
            showNextMonth();
        } else {
            showNextWeek();
        }
    }

    private void onCalendarPrevPageClick() {
        if (mAppearance.equals(Appearance.WEEK)) {
            showPrevWeek();
        } else if (mAppearance.equals(Appearance.MONTH)) {
            showPrevMonth();
        } else {
            showPrevWeek();
        }
    }

    private void showNextWeek() {
        DateTime visible = getVisibleDay();
        visible = visible.plusWeeks(1);
        mVisibleDay = visible;
        List<DayAdapterItem> days = getDaysOfWeek(visible);
        loadNextPage(days);
        updateMonthName(visible);
        revalidateNextPageView();
        revalidatePrevPageView();
    }

    private void showNextMonth() {
        DateTime visible = getVisibleDay();
        visible = visible.plusMonths(1);
        mVisibleDay = visible;
        List<DayAdapterItem> days = getDaysOfMonth(visible);
        loadNextPage(days);
        updateMonthName(visible);
        revalidateNextPageView();
        revalidatePrevPageView();
    }

    private void showPrevWeek() {
        DateTime visible = getVisibleDay();
        visible = visible.minusWeeks(1);
        mVisibleDay = visible;
        List<DayAdapterItem> days = getDaysOfWeek(visible);
        loadPreviousPage(days);
        updateMonthName(mVisibleDay);
        revalidateNextPageView();
        revalidatePrevPageView();
    }

    private void showPrevMonth() {
        DateTime visible = getVisibleDay();
        visible = visible.minusMonths(1);
        mVisibleDay = visible;
        List<DayAdapterItem> days = getDaysOfMonth(visible);
        loadPreviousPage(days);
        updateMonthName(mVisibleDay);
        revalidateNextPageView();
        revalidatePrevPageView();
    }

    private DateTime maximumDateAvailable() {
        return DateTime.now().plusMonths(mVisibleMonthAfterNow);
    }

    private DateTime minimumDateAvailable() {
        return DateTime.now().minusMonths(mVisibleMonthBeforeNow);
    }

    private void revalidateNextPageView() {
        DateTime nextPage;

        if (mAppearance.equals(Appearance.WEEK)) {
            nextPage = mVisibleDay.plusWeeks(1);
        } else if (mAppearance.equals(Appearance.MONTH)) {
            nextPage = mVisibleDay.plusMonths(1);
        } else {
            nextPage = mVisibleDay.plusWeeks(1);
        }

        enableNextMonth(!nextPage.isAfter(maximumDateAvailable()));
    }

    private void revalidatePrevPageView() {
        DateTime prevPage;

        if (mAppearance.equals(Appearance.WEEK)) {
            prevPage = mVisibleDay.minusWeeks(1);
        } else if (mAppearance.equals(Appearance.MONTH)) {
            prevPage = mVisibleDay.minusMonths(1);
        } else {
            prevPage = mVisibleDay.minusWeeks(1);
        }

        enablePrevMonth(!prevPage.isBefore(minimumDateAvailable()));
    }

    private void initCurrentDays() {
        List<DayAdapterItem> days;
        if (mAppearance.equals(Appearance.WEEK)) {
            days = getDaysOfWeek(getVisibleDay());
        } else if (mAppearance.equals(Appearance.MONTH)) {
            days = getDaysOfMonth(getVisibleDay());
        } else {
            days = getDaysOfWeek(getVisibleDay());
        }
        loadCurrentDays(days);
    }

    private void initCurrentMonthName() {
        updateMonthName(getVisibleDay());
    }

    private List<DayAdapterItem> getDaysOfMonth(DateTime monthDay) {
        int receivedMonth = monthDay.getMonthOfYear();
        List<DayAdapterItem> result = new ArrayList<>();
        DateTime firstDayOfMonth = monthDay.dayOfMonth().setCopy(1);
        DateTime tmpDate = firstDayOfMonth.dayOfWeek().setCopy(1);
        for (int i = 0; i < 42; i++) {

            DayAdapterItem model = convertToModel(tmpDate);
            int tmpMonthNumber = tmpDate.getMonthOfYear();
            if (tmpMonthNumber < receivedMonth) {
                result.add(model);
                model.setEmpty(true);
            } else if (tmpMonthNumber == receivedMonth) {
                result.add(model);
                model.setEmpty(false);
            } else {
                result.add(model);
                model.setEmpty(true);
            }
            tmpDate = tmpDate.plusDays(1);
        }
        return result;
    }

    private List<DayAdapterItem> getDaysOfWeek(DateTime weekDay) {
        int receivedWeek = weekDay.getWeekOfWeekyear();
        List<DayAdapterItem> result = new ArrayList<>();
        DateTime firstDayOfWeek = weekDay.dayOfWeek().setCopy(1);
        DateTime tmpDate = firstDayOfWeek.dayOfWeek().setCopy(1);
        for (int i = 0; i < 7; i++) {

            DayAdapterItem model = convertToModel(tmpDate);
            int tmpWeekNumber = tmpDate.getWeekOfWeekyear();
            if (tmpWeekNumber < receivedWeek) {
                result.add(model);
                model.setEmpty(true);
            } else if (tmpWeekNumber == receivedWeek) {
                result.add(model);
                model.setEmpty(false);
            } else {
                result.add(model);
                model.setEmpty(true);
            }
            tmpDate = tmpDate.plusDays(1);
        }
        return result;
    }

    private DayAdapterItem convertToModel(DateTime day) {
        day = day.millisOfDay().setCopy(0);
        DateTime today = DateTime.now().millisOfDay().setCopy(0);
        boolean isHoliday = true;
        for (DateTime workDay : mAvailableDays) {
            if (workDay.dayOfYear().equals(day.dayOfYear())
                    && workDay.year().equals(day.year())) {
                isHoliday = false;
                break;
            }
        }
        if (!isHoliday) {
            isHoliday = day.isBefore(today);
        }
        DayAdapterItem model = new DayAdapterItem();
        model.setDay(day.getDayOfMonth());
        model.setMonth(day.getMonthOfYear());
        model.setYear(day.getYear());
        model.setEmpty(false);
        model.setHoliday(isHoliday);
        model.setToday(day.getMillis() == today.getMillis());
        model.setDateTime(day);
        if (mSelectedDay != null
                && mSelectedDay.getYear() == day.getYear()
                && mSelectedDay.getDayOfYear() == day.getDayOfYear()) {
            if (!isHoliday) {
                model.setSelected(true);
            } else {
                if (mSimpleCalendarListener != null) {
                    mSimpleCalendarListener.onDaySelectionCancelled(mSelectedDay);
                }
                mSelectedDay = null;
                model.setSelected(false);
            }
        } else {
            model.setSelected(false);
        }
        return model;
    }

    private DateTime getVisibleDay() {
        if (mVisibleDay == null) {
            computeVisibleDay(DateTime.now());
        }
        return mVisibleDay;
    }

    private void computeVisibleDay(DateTime day) {

        mVisibleDay = day;

        if (mAppearance.equals(Appearance.WEEK)) {
            mVisibleDay = mVisibleDay.dayOfWeek().setCopy(1);
            mVisibleDay = mVisibleDay.secondOfDay().setCopy(1);

        } else if (mAppearance.equals(Appearance.MONTH)) {

            mVisibleDay = mVisibleDay.dayOfMonth().setCopy(1);
            mVisibleDay = mVisibleDay.secondOfDay().setCopy(1);
        } else {

            mVisibleDay = mVisibleDay.dayOfWeek().setCopy(1);
            mVisibleDay = mVisibleDay.secondOfDay().setCopy(1);
        }
    }

    private void updateMonthName(DateTime dateTime) {
        int monthInt = dateTime.getMonthOfYear();
        int index = monthInt - 1;
        setMonthName(index, dateTime.toString("yyyy"));
    }

    private DayAdapter dayAdapter() {
        return dayAdapter(true);
    }

    private DayAdapter dayAdapter(boolean active) {
        return firstListActive() ? mDayAdapter1 : mDayAdapter2;
    }

    private boolean firstListActive() {
        return rvMonth1.isEnabled();
    }

    private boolean secondListActive() {
        return rvMonth2.isEnabled();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initViews();
        initAnimation();
        initDaysList();
        initClickListeners();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_simple_calendar, this);
        rlCalendar = findViewById(R.id.rlCalendar);
        rlPreviousPage = rlCalendar.findViewById(R.id.rlPreviousPage);
        rlNextPage = rlCalendar.findViewById(R.id.rlNextPage);
        tvMonth = rlCalendar.findViewById(R.id.tvMonth);
        rvMonth1 = rlCalendar.findViewById(R.id.rvMonth1);
        rvMonth2 = rlCalendar.findViewById(R.id.rvMonth2);
    }

    private void initAnimation() {
        mSlideLeftHideAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.calendar_slide_left_animation_hide);
        mSlideLeftShowAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.calendar_slide_left_animation_show);
        mSlideRightHideAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.calendar_slide_right_animation_hide);
        mSlideRightShowAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.calendar_slide_right_animation_show);
    }

    private void initDaysList() {
        rvMonth1.setLayoutManager(new GridLayoutManager(getContext(), 7,
                LinearLayoutManager.VERTICAL, false));
        rvMonth2.setLayoutManager(new GridLayoutManager(getContext(), 7,
                LinearLayoutManager.VERTICAL, false));
        mDayAdapter1 = new DayAdapter(getContext(), this);
        mDayAdapter2 = new DayAdapter(getContext(), this);
        rvMonth1.setAdapter(mDayAdapter1);
        rvMonth2.setAdapter(mDayAdapter2);
    }

    private void initClickListeners() {
        rlNextPage.setOnClickListener(view -> onCalendarNextPageClick());
        rlPreviousPage.setOnClickListener(view -> onCalendarPrevPageClick());
    }

    private class SlideRightAnimationListener implements Animation.AnimationListener {

        private final boolean firstActive;

        SlideRightAnimationListener(boolean firstActive) {
            this.firstActive = firstActive;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (firstActive) {
                rvMonth1.setVisibility(View.GONE);
            } else {
                rvMonth2.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class SlideLeftAnimationListener implements Animation.AnimationListener {

        private final boolean firstActive;

        SlideLeftAnimationListener(boolean firstActive) {
            this.firstActive = firstActive;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (firstActive) {
                rvMonth1.setVisibility(View.GONE);
            } else {
                rvMonth2.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class ChangeAppearanceIntervalTimer extends CountDownTimer {

        ChangeAppearanceIntervalTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            mCanChangeAppearance = true;
        }
    }
}
