package ninja.be.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FluxService {
    private final Map<String, FluxSink<Object>> fluxSinkMap = new HashMap<>();

    public Flux<Object> subscribe(String key) {

        return Flux.create(flux -> {
            fluxSinkMap.put(key, flux);
            flux.onDispose(() -> fluxSinkMap.remove(key));
        });
    }

    public void notify(Object event) {
        fluxSinkMap.values().forEach(sink -> sink.next(event));
    }
}
