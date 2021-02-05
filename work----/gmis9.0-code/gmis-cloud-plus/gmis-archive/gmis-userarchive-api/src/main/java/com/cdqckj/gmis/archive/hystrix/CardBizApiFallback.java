package com.cdqckj.gmis.archive.hystrix;

import com.cdqckj.gmis.archive.CardBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.sim.dto.CardSaveDTO;
import com.cdqckj.gmis.sim.dto.CardUpdateDTO;
import com.cdqckj.gmis.sim.entity.Card;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardBizApiFallback implements CardBizApi {
    @Override
    public R<Card> saveCard(CardSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Card> updateCard(CardUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteCard(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<List<Card>> findCard(Card card) {
        return R.timeout();
    }

    @Override
    public R<Card> findCardByCustomcode(String code) {
        return R.timeout();
    }

    @Override
    public R<Card> logicalDelete(List<Id> ids) throws Exception {
        return R.timeout();
    }
}
