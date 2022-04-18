package com.eservia.model.interactors.business;

import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.model.remote.rest.RestManager;

public abstract class BaseBusinessInteractor implements BusinessInteractor {

    protected RestManager restManager;

    protected SectorInteractor sectorInteractor;

    public BaseBusinessInteractor(RestManager restManager, SectorInteractor sectorInteractor) {
        this.restManager = restManager;
        this.sectorInteractor = sectorInteractor;
    }
}
