package com.jake.flickrassignment

import com.jake.flickrassignment.data.ImageData
import com.jake.flickrassignment.main.MainViewModel
import com.jake.flickrassignment.network.ImageRepository
import com.jake.flickrassignment.network.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: ImageRepository

    @Before
    fun before() {
        repository = mockk(relaxed = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MainViewModel(repository)
    }


    @Test
    @DisplayName("Get Images return Success")
    fun imagesCallReturnSuccess() {
        coEvery { repository.getImages("porcupine") } returns NetworkResult.Success(response)
        viewModel.getImages("porcupine")
        assertEquals(response, viewModel.images)
    }

    @Test
    @DisplayName("Get Images return Failure")
    fun imagesCallReturnFailure() {
        coEvery { repository.getImages("porcupine") } returns NetworkResult.Error("data is malformed")
        viewModel.getImages("porcupine")
        assertEquals("data is malformed", viewModel.state.value.errorMessage)
    }

    @Test
    @DisplayName("Get Images return emptyList")
    fun imageClickReturnsEmptyList() {
        coEvery { repository.getImages("aewrtge") } returns NetworkResult.Success(emptyList())
        viewModel.getImages("aewrtge")
        assertEquals(0, viewModel.images.size)
    }

    @Test
    @DisplayName("Get Image from selected Image")
    fun imageClickReturnItem() {
        coEvery { repository.getImages("porcupine") } returns NetworkResult.Success(response)
        viewModel.getImages("porcupine")
        viewModel.onImageClick(14)
        assertEquals(response[14], viewModel.selectedImage)
    }

    val response = listOf(
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54156920164_c49c5b04e5_m.jpg",
            title = "PXL_20241027_035525229~2 by BHF3737",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/bhf3737/\"\u003EBHF3737\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/bhf3737/54156920164/\" title=\"20241117 Porcupine&#039;s smile\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54156920164_c49c5b04e5_m.jpg\" width=\"240\" height=\"160\" alt=\"20241117 Porcupine&#039;s smile\" /\u003E\u003C/a\u003E\u003C/p\u003E \u003Cp\u003EA Porcupine at the entrance of its den. Today it was awake and in good mood by showing its signature smile, at Carburn, #Calgary.\u003C/p\u003E ",
            publishedDate = "11/22/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54146489112_a9c92903c8_m.jpg",
            title = "North American Porcupine by David Schenfeld",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/schenfeld/\"\u003EDavid Schenfeld\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/schenfeld/54146489112/\" title=\"North American Porcupine\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54146489112_a9c92903c8_m.jpg\" width=\"240\" height=\"160\" alt=\"North American Porcupine\" /\u003E\u003C/a\u003E\u003C/p\u003E \u003Cp\u003ECadillac Mountain, Acadia National Park, Maine\u003C/p\u003E ",
            publishedDate = "11/18/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54142899515_8cc3c29fea_m.jpg",
            title = "Porcupine by John Hallam Images",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/johnhallamimages/\"\u003EJohn Hallam Images\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/johnhallamimages/54142899515/\" title=\"Porcupine\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54142899515_8cc3c29fea_m.jpg\" width=\"240\" height=\"217\" alt=\"Porcupine\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/15/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54139627800_15592300c0_m.jpg",
            title = "Brazilian Porcupine - Black and White by Paul Cottis",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/paulcottis/\"\u003EPaul Cottis\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/paulcottis/54139627800/\" title=\"Brazilian Porcupine - Black and White\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54139627800_15592300c0_m.jpg\" width=\"240\" height=\"153\" alt=\"Brazilian Porcupine - Black and White\" /\u003E\u003C/a\u003E\u003C/p\u003E \u003Cp\u003EEl Dorado Lodge, Sierra Nevada de Santa Marta, Colombia\u003C/p\u003E ",
            publishedDate = "11/14/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54139170151_e26e5b3e37_m.jpg",
            title = "Brazilian Porcupine climbing in the branches showing its pink underside by Paul Cottis",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/paulcottis/\"\u003EPaul Cottis\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/paulcottis/54139170151/\" title=\"Brazilian Porcupine climbing in the branches showing its pink underside\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54139170151_e26e5b3e37_m.jpg\" width=\"240\" height=\"172\" alt=\"Brazilian Porcupine climbing in the branches showing its pink underside\" /\u003E\u003C/a\u003E\u003C/p\u003E \u003Cp\u003EEl Dorado Lodge, Sierra Nevada de Santa Marta, Colombia\u003C/p\u003E ",
            publishedDate = "11/14/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54138313707_7fa059a5ee_m.jpg",
            title = "Brazilian Porcupine looking down from branch by Paul Cottis",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/paulcottis/\"\u003EPaul Cottis\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/paulcottis/54138313707/\" title=\"Brazilian Porcupine looking down from branch\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54138313707_7fa059a5ee_m.jpg\" width=\"240\" height=\"157\" alt=\"Brazilian Porcupine looking down from branch\" /\u003E\u003C/a\u003E\u003C/p\u003E \u003Cp\u003EEl Dorado Lodge, Sierra Nevada de Santa Marta, Colombia\u003C/p\u003E ",
            publishedDate = "11/14/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134476660_7089499e9e_m.jpg",
            title = "supplies-PXL_20241023_035547413 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134476660/\" title=\"supplies-PXL_20241023_035547413\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134476660_7089499e9e_m.jpg\" width=\"147\" height=\"240\" alt=\"supplies-PXL_20241023_035547413\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134335259_ae72147003_m.jpg",
            title = "PXL_20241027_055407058 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134335259/\" title=\"PXL_20241027_055407058\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134335259_ae72147003_m.jpg\" width=\"175\" height=\"240\" alt=\"PXL_20241027_055407058\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54133150537_980e012272_m.jpg",
            title = "head base fur marked PXL_20241024_230221256 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54133150537/\" title=\"head base fur marked PXL_20241024_230221256\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54133150537_980e012272_m.jpg\" width=\"240\" height=\"181\" alt=\"head base fur marked PXL_20241024_230221256\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134281203_7d51dc6070_m.jpg",
            title = "PXL_20241027_055427996~2 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134281203/\" title=\"PXL_20241027_055427996~2\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134281203_7d51dc6070_m.jpg\" width=\"176\" height=\"240\" alt=\"PXL_20241027_055427996~2\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54133994011_7855442f03_m.jpg",
            title = "head base quill test PXL_20241024_230555997 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54133994011/\" title=\"head base quill test PXL_20241024_230555997\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54133994011_7855442f03_m.jpg\" width=\"203\" height=\"240\" alt=\"head base quill test PXL_20241024_230555997\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134452825_57628a4ca5_m.jpg",
            title = "head base dry fit front PXL_20241024_222645000 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134452825/\" title=\"head base dry fit front PXL_20241024_222645000\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134452825_57628a4ca5_m.jpg\" width=\"228\" height=\"240\" alt=\"head base dry fit front PXL_20241024_222645000\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54133150387_d13ceba701_m.jpg",
            title = "PXL_20241027_055422693~2 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54133150387/\" title=\"PXL_20241027_055422693~2\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54133150387_d13ceba701_m.jpg\" width=\"159\" height=\"240\" alt=\"PXL_20241027_055422693~2\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134281308_b109d25c2f_m.jpg",
            title = "spine base dry fit PXL_20241024_232248381 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134281308/\" title=\"spine base dry fit PXL_20241024_232248381\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134281308_b109d25c2f_m.jpg\" width=\"240\" height=\"184\" alt=\"spine base dry fit PXL_20241024_232248381\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134281453_13aebf24e5_m.jpg",
            title = "head base finished PXL_20241026_162714115 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134281453/\" title=\"head base finished PXL_20241026_162714115\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134281453_13aebf24e5_m.jpg\" width=\"240\" height=\"220\" alt=\"head base finished PXL_20241026_162714115\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54133150592_99b1e309cf_m.jpg",
            title = "velcro taped hoodie PXL_20241026_195822199 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54133150592/\" title=\"velcro taped hoodie PXL_20241026_195822199\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54133150592_99b1e309cf_m.jpg\" width=\"139\" height=\"240\" alt=\"velcro taped hoodie PXL_20241026_195822199\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134452785_a41ef23f9f_m.jpg",
            title = "PXL_20241027_035525229~2 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134452785/\" title=\"PXL_20241027_035525229~2\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134452785_a41ef23f9f_m.jpg\" width=\"209\" height=\"240\" alt=\"PXL_20241027_035525229~2\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134335009_b5ddb7fee9_m.jpg",
            title = "hoodie base fur cut PXL_20241024_221118991 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54134335009/\" title=\"hoodie base fur cut PXL_20241024_221118991\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54134335009_b5ddb7fee9_m.jpg\" width=\"149\" height=\"240\" alt=\"hoodie base fur cut PXL_20241024_221118991\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54134452980_9741e66343_m.jpg",
            title = "PXL_20241027_193704817 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/bhf3737/\"\u003EBHF3737\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/bhf3737/54156920164/\" title=\"20241117 Porcupine&#039;s smile\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54156920164_c49c5b04e5_m.jpg\" width=\"240\" height=\"160\" alt=\"20241117 Porcupine&#039;s smile\" /\u003E\u003C/a\u003E\u003C/p\u003E \u003Cp\u003EA Porcupine at the entrance of its den. Today it was awake and in good mood by showing its signature smile, at Carburn, #Calgary.\u003C/p\u003E ",
            publishedDate = "11/22/2024"
        ),
        ImageData(
            imgUrl = "https://live.staticflickr.com/65535/54133994106_e3b740cfb9_m.jpg",
            title = "gluing zip ties PXL_20241026_160018209 by joelogon",
            description = " \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/people/joelogon/\"\u003Ejoelogon\u003C/a\u003E posted a photo:\u003C/p\u003E \u003Cp\u003E\u003Ca href=\"https://www.flickr.com/photos/joelogon/54133994106/\" title=\"gluing zip ties PXL_20241026_160018209\"\u003E\u003Cimg src=\"https://live.staticflickr.com/65535/54133994106_e3b740cfb9_m.jpg\" width=\"240\" height=\"184\" alt=\"gluing zip ties PXL_20241026_160018209\" /\u003E\u003C/a\u003E\u003C/p\u003E ",
            publishedDate = "11/12/2024"
        )
    )
}