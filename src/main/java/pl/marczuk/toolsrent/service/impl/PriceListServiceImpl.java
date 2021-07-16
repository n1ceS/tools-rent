package pl.marczuk.toolsrent.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marczuk.toolsrent.model.PriceList;
import pl.marczuk.toolsrent.repository.PriceListRepository;
import pl.marczuk.toolsrent.service.PriceListService;

@Service
public class PriceListServiceImpl implements PriceListService {
    private final PriceListRepository priceListRepository;

    public PriceListServiceImpl(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    @Override
    @Transactional
    public PriceList addPriceList(PriceList priceList) {
        return priceListRepository.save(priceList);
    }
}
