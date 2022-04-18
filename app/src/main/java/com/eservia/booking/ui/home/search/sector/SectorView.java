package com.eservia.booking.ui.home.search.sector;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.model.entity.BusinessSector;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SectorView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onSectorsLoadingSuccess(List<BusinessSector> sectors, boolean isMale);

    @StateStrategyType(value = SkipStrategy.class)
    void onSectorsLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void goToSearch(BusinessSector sector);

    @StateStrategyType(value = SkipStrategy.class)
    void goToPoints();

    @StateStrategyType(value = SkipStrategy.class)
    void goToDiscounts();

    @StateStrategyType(value = SkipStrategy.class)
    void goToWallet();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedCity(@Nullable String city);

    @StateStrategyType(value = SkipStrategy.class)
    void showCitiesDialog(List<SheetAdapterItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideSheetDialog();
}
