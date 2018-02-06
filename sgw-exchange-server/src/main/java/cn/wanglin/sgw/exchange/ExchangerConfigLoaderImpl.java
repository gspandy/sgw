package cn.wanglin.sgw.exchange;

import cn.wanglin.sgw.exchange.exception.NoSuchExchangerException;
import cn.wanglin.sgw.exchange.repo.ExchangerConfigEntity;
import cn.wanglin.sgw.exchange.repo.ExchangerConfigRepo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ExchangerConfigLoaderImpl implements ExchangerConfigLoader {

    @Autowired
    ExchangerConfigRepo exchangerConfigRepo;

    @Override
    public ExchangerConfig getExchangerConfig(String exchangerCode) throws NoSuchExchangerException {
        ExchangerConfigEntity entity =  exchangerConfigRepo.getOne(exchangerCode);
        if(null == entity)
            throw new NoSuchExchangerException(exchangerCode);
        return entity.toDto();
    }
}
