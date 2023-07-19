package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.repository.EmitterRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SseService {
    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe(String key) {
        SseEmitter emitter = createEmitter(key);

//        sendToClient(key, "EventStream Created. [userId=" + key + "]");
        return emitter;
    }

    public void notify(Object event) {
        sendToAllClient(event);
    }

    private void sendToClient(String key, Object data) {
//        SseEmitter emitter = emitterRepository.get(key);
        Map<String, SseEmitter> all = emitterRepository.getAll();
        if (!all.isEmpty()) {
            for (String s : all.keySet()) {
                try {
                    System.out.println(s);
                    all.get(s).send(data, MediaType.APPLICATION_JSON);
//                    all.get(s).send(SseEmitter.event().id(s).name("sse").data(data));
                } catch (IOException exception) {
                    emitterRepository.deleteById(s);
//                    all.get(s).completeWithError(exception);
                }
            }
        }
//        if (emitter != null) {
//            try {
//                emitter.send(SseEmitter.event().id(key).name("sse").data(data));
//            } catch (IOException exception) {
//                emitterRepository.deleteById(key);
//                emitter.completeWithError(exception);
//            }
//        }
    }

    private void sendToAllClient(Object data) {
        Map<String, SseEmitter> emitters = emitterRepository.getAll();
        Set<String> deadIds = new HashSet<>();

        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(data, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(id);
            }
        });

        deadIds.forEach(emitters::remove);
    }

    private SseEmitter createEmitter(String key) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(key, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(key));
        emitter.onTimeout(() -> emitterRepository.deleteById(key));

        return emitter;
    }
}