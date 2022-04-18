package com.eservia.model.local.menu;

import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoCategory_;
import com.eservia.model.entity.OrderRestoDimension;
import com.eservia.model.entity.OrderRestoDimension_;
import com.eservia.model.entity.OrderRestoMenuData;
import com.eservia.model.entity.OrderRestoMenuData_;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.OrderRestoNomenclatureOption;
import com.eservia.model.entity.OrderRestoNomenclatureOption_;
import com.eservia.model.entity.OrderRestoNomenclature_;
import com.eservia.model.entity.OrderRestoOption;
import com.eservia.model.entity.OrderRestoOption_;
import com.eservia.model.entity.OrderRestoPortionGradation;
import com.eservia.model.entity.OrderRestoPortionGradation_;
import com.eservia.model.entity.OrderRestoSizePrice;
import com.eservia.model.entity.OrderRestoSizePrice_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Observable;

public class MenuRepositoryImpl implements MenuRepository {

    private final BoxStore boxStore;

    private final Box<OrderRestoMenuData> menuDataBox;
    private final Box<OrderRestoCategory> categoryBox;
    private final Box<OrderRestoDimension> dimensionBox;
    private final Box<OrderRestoNomenclature> nomenclatureBox;
    private final Box<OrderRestoNomenclatureOption> nomenclatureOptionBox;
    private final Box<OrderRestoOption> optionBox;
    private final Box<OrderRestoPortionGradation> gradationBox;
    private final Box<OrderRestoSizePrice> sizePriceBox;

    public MenuRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
        this.menuDataBox = boxStore.boxFor(OrderRestoMenuData.class);
        this.categoryBox = boxStore.boxFor(OrderRestoCategory.class);
        this.dimensionBox = boxStore.boxFor(OrderRestoDimension.class);
        this.nomenclatureBox = boxStore.boxFor(OrderRestoNomenclature.class);
        this.nomenclatureOptionBox = boxStore.boxFor(OrderRestoNomenclatureOption.class);
        this.optionBox = boxStore.boxFor(OrderRestoOption.class);
        this.gradationBox = boxStore.boxFor(OrderRestoPortionGradation.class);
        this.sizePriceBox = boxStore.boxFor(OrderRestoSizePrice.class);
    }

    @Override
    public Observable<Boolean> saveMenu(OrderRestoMenuData menuData, long businessId, long addressId) {
        return Observable.fromCallable(() -> {
            clearAllMenuBoxesForBusinessId(businessId);
            decorateMenuItems(menuData, businessId, addressId);
            putMenuItems(menuData);
            buildRelations(businessId);
            return Boolean.TRUE;
        });
    }

    @Override
    public Observable<Long> getMenuVersion(long businessId) {
        return Observable.fromCallable(() -> {
            OrderRestoMenuData menuData = menuDataBox.query()
                    .equal(OrderRestoMenuData_.businessId, businessId)
                    .build().findFirst();
            return menuData != null ? menuData.getVersion() : -1L;
        });
    }

    @Override
    public Observable<List<OrderRestoNomenclature>> findNomenclaturesWithId(long id,
                                                                            long businessId) {
        return Observable.just(nomenclatureBox.query()
                .equal(OrderRestoNomenclature_.businessId, businessId)
                .equal(OrderRestoNomenclature_.id, id)
                .build().find());
    }

    @Override
    public Observable<List<OrderRestoNomenclature>> findNomenclaturesWithId(long[] ids,
                                                                            long businessId) {
        return Observable.just(nomenclatureBox.query()
                .equal(OrderRestoNomenclature_.businessId, businessId)
                .in(OrderRestoNomenclature_.id, ids)
                .build().find());
    }

    private void clearAllMenuBoxesForBusinessId(long businessId) {
        boxStore.runInTx(() -> {
            menuDataBox.query().equal(OrderRestoMenuData_.businessId, businessId)
                    .build().remove();
            categoryBox.query().equal(OrderRestoCategory_.businessId, businessId)
                    .build().remove();
            dimensionBox.query().equal(OrderRestoDimension_.businessId, businessId)
                    .build().remove();
            nomenclatureBox.query().equal(OrderRestoNomenclature_.businessId, businessId)
                    .build().remove();
            nomenclatureOptionBox.query().equal(OrderRestoNomenclatureOption_.businessId, businessId)
                    .build().remove();
            optionBox.query().equal(OrderRestoOption_.businessId, businessId)
                    .build().remove();
            gradationBox.query().equal(OrderRestoPortionGradation_.businessId, businessId)
                    .build().remove();
            sizePriceBox.query().equal(OrderRestoSizePrice_.businessId, businessId)
                    .build().remove();
        });
    }

    private void decorateMenuItems(OrderRestoMenuData data, long businessId, long addressId) {
        data.setBusinessId(businessId);
        data.setAddressId(addressId);

        for (OrderRestoCategory category : data.getCategories()) {
            category.setBusinessId(businessId);
            category.setAddressId(addressId);
        }
        for (OrderRestoDimension dimension : data.getDimensions()) {
            dimension.setBusinessId(businessId);
            dimension.setAddressId(addressId);
        }
        for (OrderRestoNomenclature nomenclature : data.getNomenclatures()) {
            nomenclature.setBusinessId(businessId);
            nomenclature.setAddressId(addressId);
        }
        for (OrderRestoNomenclatureOption nomenclatureOption : data.getNomenclatureOptions()) {
            nomenclatureOption.setBusinessId(businessId);
            nomenclatureOption.setAddressId(addressId);
        }
        for (OrderRestoOption option : data.getOptions()) {
            option.setBusinessId(businessId);
            option.setAddressId(addressId);
        }
        for (OrderRestoPortionGradation gradation : data.getPortionGradations()) {
            gradation.setBusinessId(businessId);
            gradation.setAddressId(addressId);
        }
        for (OrderRestoSizePrice sizePrice : data.getSizePrices()) {
            sizePrice.setBusinessId(businessId);
            sizePrice.setAddressId(addressId);
        }
    }

    private void putMenuItems(OrderRestoMenuData data) {
        boxStore.runInTx(() -> {
            menuDataBox.put(data);
            categoryBox.put(data.getCategories());
            dimensionBox.put(data.getDimensions());
            nomenclatureBox.put(data.getNomenclatures());
            nomenclatureOptionBox.put(data.getNomenclatureOptions());
            optionBox.put(data.getOptions());
            gradationBox.put(data.getPortionGradations());
            sizePriceBox.put(data.getSizePrices());
        });
    }

    private void buildRelations(long businessId) {
        buildOrderRestoNomenclatureRelations(businessId);
    }

    private void buildOrderRestoNomenclatureRelations(long businessId) {
        boxStore.runInTx(() -> {
            List<OrderRestoNomenclature> nomenclatures = nomenclatureBox.query()
                    .equal(OrderRestoNomenclature_.businessId, businessId)
                    .build().find();

            for (OrderRestoNomenclature nomenclature : nomenclatures) {
                buildOrderRestoNomenclatureRelations(nomenclature, businessId);
            }
            nomenclatureBox.put(nomenclatures);
        });
    }

    private void buildOrderRestoNomenclatureRelations(OrderRestoNomenclature nomenclature,
                                                      long businessId) {

        if (nomenclature.getParentId() != null) {
            OrderRestoCategory category = categoryBox.query()
                    .equal(OrderRestoCategory_.businessId, businessId)
                    .equal(OrderRestoCategory_.id, nomenclature.getParentId())
                    .build().findFirst();
            nomenclature.getCategory().setTarget(category);
        }

        if (nomenclature.getADimensionId() != null) {
            OrderRestoDimension dimension = dimensionBox.query()
                    .equal(OrderRestoDimension_.businessId, businessId)
                    .equal(OrderRestoDimension_.id, nomenclature.getADimensionId())
                    .build().findFirst();
            nomenclature.getDimension().setTarget(dimension);
        }

        if (nomenclature.getPortionGradationId() != null) {
            OrderRestoPortionGradation gradation = gradationBox.query()
                    .equal(OrderRestoPortionGradation_.businessId, businessId)
                    .equal(OrderRestoPortionGradation_.id, nomenclature.getPortionGradationId())
                    .build().findFirst();
            nomenclature.getPortionGradation().setTarget(gradation);
        }

        List<OrderRestoSizePrice> sizePrices = sizePriceBox.query()
                .equal(OrderRestoSizePrice_.businessId, businessId)
                .equal(OrderRestoSizePrice_.nomenclatureId, nomenclature.getId())
                .build().find();
        for (OrderRestoSizePrice sizePrice : sizePrices) {
            nomenclature.getSizePrices().add(sizePrice);
        }
    }
}
