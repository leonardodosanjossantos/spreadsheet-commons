package br.com.leonardo.commons.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.leonardo.commons.model.FilePathData;

@Service
public class RabbitMQSender {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${product.exchange}")
	private String exchange;
	
	@Value("${product.routing.key}")
	private String routingkey;
	
	public void send(final FilePathData data) {
		rabbitTemplate.convertAndSend(exchange, routingkey, data);
		log.info("Send msg = " + data);
	    
	}
}