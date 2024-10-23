package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;


import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactRepository;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactSource;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import io.netty.util.internal.ObjectUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
class RedisContactRepository implements ContactRepository {

    private final ValueOperations<ContactSource, List<Contact>> cacheValueOperations;

    public RedisContactRepository(
            @Autowired RedisTemplate<ContactSource, List<Contact>> redisTemplate
    ) {
        this.cacheValueOperations = redisTemplate.opsForValue();
    }

    @Override
    public List<Contact> findBySource(final ContactSource contactSource) {
        return ObjectUtils.defaultIfNull(cacheValueOperations.get(contactSource), new ArrayList<>());
    }

    @Override
    public void saveAll(List<Contact> contacts) {

        // this will need to change if the system were to support other sources
        cacheValueOperations.set(ContactSource.KENECT_LABS, contacts);
    }



}
