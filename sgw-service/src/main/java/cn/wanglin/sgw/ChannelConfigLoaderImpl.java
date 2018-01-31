package cn.wanglin.sgw;

import cn.wanglin.sgw.exception.NoSuchChannelException;
import cn.wanglin.sgw.repo.ChannelConfigEntity;
import cn.wanglin.sgw.repo.ChannelConfigRepo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ChannelConfigLoaderImpl implements ChannelConfigLoader {

    @Autowired
    ChannelConfigRepo channelConfigRepo;

    @Override
    public ChannelConfig getChannelConfig(String channelCode) throws NoSuchChannelException {
        ChannelConfigEntity entity =  channelConfigRepo.getOne(channelCode);
        if(null == entity)
            throw new NoSuchChannelException(channelCode);
        return entity.toDto();
    }
}
