package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.repository.EmitterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SseService {
    private final EmitterRepository emitterRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(String key) {
        SseEmitter emitter = createEmitter(key);

//        sendToClient(key, "EventStream Created. [key=" + key + "]");
        return emitter;
    }

    public void notify(String key, Object event) {
        sendToClient(key, event);
    }

    private void sendToClient(String key, Object data) {
        SseEmitter emitter = emitterRepository.get(key);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(key)).name("sse").data(data));
            } catch (IOException exception) {
                emitterRepository.deleteById(key);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter(String key) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(key, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(key));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(key));

        return emitter;
    }
}
