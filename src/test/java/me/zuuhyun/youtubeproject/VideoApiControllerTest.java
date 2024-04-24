package me.zuuhyun.youtubeproject;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.dto.AddVideoRequest;
import me.zuuhyun.youtubeproject.dto.UpdateVideoRequest;
import me.zuuhyun.youtubeproject.repository.VideoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class VideoApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    VideoRepository videoRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        videoRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addVideo() throws Exception {
        // given
        final String url = "/api/videos";
        final String title = "title";
        final String content = "content";
        final long length = 1234;
        final AddVideoRequest userRequest = new AddVideoRequest(title,content,length);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));


        // then
        result.andExpect(status().isCreated());
        List<Video> articles = videoRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
        assertThat(articles.get(0).getLength()).isEqualTo(length);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllVideos() throws Exception {
        // given
        final String url = "/api/videos";
        final String title = "title";
        final String content = "content";
        final long length = 1234;
        videoRepository.save(Video.builder()
                .title(title)
                .content(content)
                .length(length)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].length").value(length))
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));

    }


    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findVideo() throws Exception {
        // given
        final String url = "/api/videos/{id}";
        final String title = "title";
        final String content = "content";
        final long length = 1234;
        Video savedVideo = videoRepository.save(Video.builder()
                .title(title)
                .content(content)
                .length(length)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedVideo.
                getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(length))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteVideo() throws Exception {
        // given
        final String url = "/api/videos/{id}";
        final String title = "title";
        final String content = "content";
        final long length = 1234;
        Video savedArticle = videoRepository.save(Video.builder()
                .title(title)
                .content(content)
                .length(length)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateVideo() throws Exception {
        // given
        final String url = "/api/videos/{id}";
        final String title = "title";
        final String content = "content";
        final long length = 1234;
        Video savedArticle = videoRepository.save(Video.builder()
                .title(title)
                .content(content)
                .length(length)
                .build());
        final String newTitle = "new title";
        final String newContent = "new content";
        final long newLength = 1234;
        UpdateVideoRequest request = new UpdateVideoRequest(newTitle,
                newContent, newLength);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        Video video = videoRepository.findById(savedArticle.getId()).get();
        assertThat(video.getTitle()).isEqualTo(newTitle);
        assertThat(video.getContent()).isEqualTo(newContent);
    }
}
