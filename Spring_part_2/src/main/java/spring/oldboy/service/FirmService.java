package spring.oldboy.service;

import spring.oldboy.repository.FirmRepository;

public class FirmService {

    private final FirmRepository firmRepository;

    public FirmService(FirmRepository firmRepository) {
        this.firmRepository = firmRepository;
    }

}