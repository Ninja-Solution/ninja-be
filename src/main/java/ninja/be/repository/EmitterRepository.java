package ninja.be.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(String key, SseEmitter emitter) {
        emitters.put(key, emitter);
    }

    public void deleteById(String key) {
        emitters.remove(key);
    }

    public SseEmitter get(String key) {
        return emitters.get(key);
    }
}