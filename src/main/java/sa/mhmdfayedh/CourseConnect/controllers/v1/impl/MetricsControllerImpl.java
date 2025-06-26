package sa.mhmdfayedh.CourseConnect.controllers.v1.impl;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.mhmdfayedh.CourseConnect.controllers.v1.intefaces.MetricsController;
import sa.mhmdfayedh.CourseConnect.dto.v1.SystemMetricsDTO;

@RestController
@RequestMapping("/api/admin")
public class MetricsControllerImpl implements MetricsController {

    private final MeterRegistry meterRegistry;

    public MetricsControllerImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    @GetMapping("/system-metrics")
    public SystemMetricsDTO getMetrics() {
        SystemMetricsDTO systemMetricsDTO = new SystemMetricsDTO();

        systemMetricsDTO.setHeapMemoryUsed(getGauge("jvm.memory.used", "area", "heap"));
        systemMetricsDTO.setCpuUsage(getGauge("system.cpu.usage"));
        systemMetricsDTO.setActiveDbConnections(getGauge("hikaricp.connections.active"));
        systemMetricsDTO.setUptimeInSeconds(getGauge("process.uptime"));
        systemMetricsDTO.setRequestCount(getCounter("http.server.requests"));

        return systemMetricsDTO;
    }

    private Double getGauge(String name) {
        Gauge gauge = meterRegistry.find(name).gauge();
        return gauge != null ? gauge.value() : null;
    }

    private Double getGauge(String name, String tag, String value) {
        Gauge gauge = meterRegistry.find(name).tag(tag, value).gauge();
        return gauge != null ? gauge.value() : null;
    }

    private Double getCounter(String name) {
        Counter counter = meterRegistry.find(name).counter();
        return counter != null ? counter.count() : null;
    }
}
