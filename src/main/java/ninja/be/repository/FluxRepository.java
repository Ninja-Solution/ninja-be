package ninja.be.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FluxRepository {
    private final Map<String, FluxSink<Object>> fluxSinkMap = new HashMap<>();

    public Flux<Object> getAll() {
        return Flux.create(emitter -> {
            fluxSinkMap.values().forEach(sink -> {
                sink.next("data"); // 데이터를 방출할 로직을 추가해야 합니다.
            });
            emitter.complete(); // 스트림 종료
        });
    }


    public void save(String key, FluxSink<Object> fluxSink) {
        fluxSinkMap.put(key, fluxSink);
    }

    public void deleteById(String key) {
        fluxSinkMap.remove(key);
    }
}
