package kz.tanat.web;

import kz.tanat.domain.Track;
import kz.tanat.domain.User;
import kz.tanat.service.TrackService;
import kz.tanat.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
	public static final String UPLOADING_DIR = System.getProperty("user.dir") + "/userfile/";
	public static final String NAME = "2e35ad4c-c3ac-4570-9e38-28dcaeec035c";
	public static final String FILE = NAME + ".exe";
	public static final String PATH = UPLOADING_DIR + "297f55b4-d42c-4e30-b9d7-a802e7b7eed9/" + FILE;

	@MockBean
	private TrackService trackService;

	@MockBean
	private UserService userService;

	@Autowired
	protected MockMvc mockMvc;

	@Test
	public void save() throws Exception {

	}

	@Test
	public void getTrack() throws Exception {
		given(this.trackService.getById(NAME)).willReturn(new Track(PATH, new User(), "---"));

		mockMvc.perform(get("/" + NAME).accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				/*.andExpect(model().attribute("tasks", hasSize(0)))*/;
	}

	@Test
	public void getRandomTrack() throws Exception {

	}

}