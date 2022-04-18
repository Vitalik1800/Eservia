package com.eservia.booking.ui.staff.beauty.info;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.eservia.booking.R;
import com.eservia.booking.ui.gallery.GalleryActivity;
import com.eservia.booking.ui.gallery.GalleryExtra;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.utils.StringUtil;

import moxy.presenter.InjectPresenter;

public class StaffInfoBeautyFragment extends BaseHomeFragment implements StaffInfoBeautyView {

    LinearLayout fragment;
    RelativeLayout rlCardHolderMainInfo;
    CardView cvContainerMainInfo;
    RelativeLayout rlInfoHeader;
    ImageView ivImage;
    TextView tvName;
    TextView tvPosition;
    TextView tvDescription;

    @InjectPresenter
    StaffInfoBeautyPresenter mPresenter;

    private Activity mActivity;

    public static StaffInfoBeautyFragment newInstance() {
        return new StaffInfoBeautyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_info_beauty, container, false);
        mActivity = getActivity();
        mPresenter = new StaffInfoBeautyPresenter();
        setUnbinder(ButterKnife.bind(this, view));
        fragment = view.findViewById(R.id.fragment);
        rlCardHolderMainInfo = view.findViewById(R.id.rlCardHolderMainInfo);
        cvContainerMainInfo = view.findViewById(R.id.cvContainerMainInfo);
        rlInfoHeader = view.findViewById(R.id.rlInfoHeader);
        ivImage = view.findViewById(R.id.ivImage);
        tvName = view.findViewById(R.id.tvName);
        tvPosition = view.findViewById(R.id.tvPosition);
        tvDescription = view.findViewById(R.id.tvDescription);
        rlInfoHeader.setOnClickListener(v -> onHeaderClick());
        initViews();
        return view;
    }

    public void onHeaderClick() {
        mPresenter.onStaffImageClick();
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onStaff(@Nullable BeautyStaff staff)  {
        if (staff == null) {
            fragment.setVisibility(View.INVISIBLE);
        } else {
            fragment.setVisibility(View.VISIBLE);

            try {
                bindStaff(staff);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showGalleryActivity(GalleryExtra extra) {
        GalleryActivity.start(mActivity, extra);
    }

    private void bindStaff(BeautyStaff staff) throws ClassNotFoundException {
        tvName.setText(BusinessUtil.getStaffFullName(staff.getFirstName(), staff.getLastName()));

        if (!StringUtil.isEmpty(staff.getPosition())) {
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText(staff.getPosition());
        } else {
            tvPosition.setVisibility(View.GONE);
            tvPosition.setText("");
        }

        if (!StringUtil.isEmpty(staff.getDescription())) {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(staff.getDescription());
        } else {
            tvDescription.setVisibility(View.GONE);
            tvDescription.setText("");
        }

        ImageUtil.displayStaffImageRound(mActivity, ivImage, staff.getPhoto(),
                R.drawable.user_man_big);
    }

    private void initViews() {
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderMainInfo, cvContainerMainInfo);
    }
}
