package spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.request.MovieSessionRequestDto;
import spring.dto.response.MovieSessionResponseDto;
import spring.model.MovieSession;
import spring.service.MovieSessionService;
import spring.service.mapper.MovieSessionMapper;
import spring.util.DateTimePatternUtil;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private static final String DATE_PATTERN = DateTimePatternUtil.DATE_PATTERN;
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping
    public MovieSessionResponseDto add(@RequestBody @Valid MovieSessionRequestDto requestDto) {
        MovieSession movieSession = movieSessionMapper.mapToModel(requestDto);
        movieSessionService.add(movieSession);
        return movieSessionMapper.mapToDto(movieSession);
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAll(@RequestParam Long movieId,
                                                @RequestParam
                                                @DateTimeFormat(pattern = DATE_PATTERN)
                                                        LocalDate date) {
        return movieSessionService.findAvailableSessions(movieId, date)
                .stream()
                .map(movieSessionMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public MovieSessionResponseDto update(@PathVariable Long id,
                                          @RequestBody @Valid MovieSessionRequestDto requestDto) {
        MovieSession movieSession = movieSessionMapper.mapToModel(requestDto);
        movieSession.setId(id);
        movieSessionService.update(movieSession);
        return movieSessionMapper.mapToDto(movieSession);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieSessionService.delete(id);
    }
}
