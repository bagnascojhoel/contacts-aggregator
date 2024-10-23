package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactSource;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.serializer.DefaultSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.util.Collection;
import java.util.List;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<ContactSource, List<Contact>> redisTemplate(
            final RedisConnectionFactory redisConnectionFactory,
            final ObjectMapper objectMapper
    ) {
        RedisTemplate<ContactSource, List<Contact>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    private ObjectMapper typeResolverCopy(final ObjectMapper objectMapper) {
        final ObjectMapper result = objectMapper.copy();
//        TypeResolverBuilder<?> typeResolverBuilder = ObjectMapper.DefaultTypeResolverBuilder.construct(
//                ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT,
//                result.getPolymorphicTypeValidator());
//        typeResolverBuilder = typeResolverBuilder.init(JsonTypeInfo.Id.CLASS, null);
//        typeResolverBuilder = typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
        result.activateDefaultTyping(new LaissezFaireSubTypeValidator(), ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
        return result;
    }
}
