package com.eservia.booking.ui.dish_details;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.OrderRestoNomenclature;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface DishDetailsView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishImage(String photoPath) ;

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishName(String name);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishMinWeight(OrderRestoNomenclature dish);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishWeight(OrderRestoNomenclature dish, Double size);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishCookingTime(OrderRestoNomenclature dish);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishDescription(String description);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindNumberOfPortions(int numberOfPortions);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindDishSizePrices(OrderRestoNomenclature dish, int selectedItemPosition);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindTotalPrice(double totalPrice);

    @StateStrategyType(value = SkipStrategy.class)
    void goBack();

    @StateStrategyType(value = SkipStrategy.class)
    void showAddedToCartMessage();

    @StateStrategyType(value = SkipStrategy.class)
    void showAddedToCartErrorMessage();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setMaxPortionsCount(int maxPortionsCount);

    @StateStrategyType(value = SkipStrategy.class)
    void showExceededMaxAmountOfPortionsError(int maxAmount);
}
