package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringModelParameterService;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringModelParameterDao;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ScoringModelParameterServiceImpl implements ScoringModelParameterService {
    ScoringModelParameterDao scoringModelParameterDao;
    ScoringModelDao scoringModelDao;

    @Autowired
    public void setScoringModelParameterDao(ScoringModelParameterDao scoringModelParameterDao){
        this.scoringModelParameterDao = scoringModelParameterDao;
    }

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModelParameter getModelAttributeValue(Long modelId, Long idInDataBase){
        return scoringModelParameterDao.getModelAttributeValue(modelId, idInDataBase);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void setChangedScoreInAttributeValue(Long modelId, Long idInDataBase,
                                                ScoringModelParameter scoringModelParameter){
        ScoringModelParameter scoringModelParameterFromDB =
                scoringModelParameterDao.getModelAttributeValue(modelId, idInDataBase);
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(modelId);
        scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringModelParameterFromDB.setScore(scoringModelParameter.getScore());
        scoringModelParameterFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringModelDao.update(scoringModelFromDB);
        scoringModelParameterDao.update(scoringModelParameterFromDB);
    }
}
