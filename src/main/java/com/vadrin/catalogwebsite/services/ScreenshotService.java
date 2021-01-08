package com.vadrin.catalogwebsite.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ScreenshotService {

	private static final int RETRYCOUNT = 5;
	private static final String DEFAULTBASE64IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAcIAAAEGCAYAAAD2YZXnAAAAJXRFWHRTb2Z0d2FyZQBUaHVtLmlvIHNjcmVlbnNob3QgZ2VuZXJhdG9yVy5TPAAAZydJREFUeF7tnQeUXEeV970LC2eXs8DCwrJLMMkmLAsGDBiTMbbJGWyCcc5BloOcLeOcc8DGIGcccZZlW7aClaUZSZNzznkkOeAPfL/3q547elMdpqene6Zf973n/M/rfq9eVb2q++6/6lZ4O+ywww4yU/jSl74k+++/v8FgMBgMeYMdfLLKJYwIDQaDwZBv2MEnq1zCiNBgMBgM+YYdfLLKJYwIDQaDwZBv2MEnK8W73vUu+Z//+R9505veJDvuuKO8+c1vjgszVaQiwgMOOEDmzp3rjkcffbTccccdcWEMBoPBYMg2dvDJSnHGGWfILbfcIu94xzukvr5edtlll7gwU0UqIoQEW1tbZf78+XLUUUdJQ0NDXBgfBx54oFx//fVx5w0Gg8FgSBc7+GSlOOSQQ1yvbPfdd5e6ujr58Ic/LLfffrvcdNNNsvfee0tNTY0jLcI89thj8s1vflMWLFggDz30UNLeYyoivOKKK+TGG2+UZ5991hFhc3Oz3HffffLwww87PPnkk3LPPfdIWVmZC8v1yy+/XGpra10P0o/PYDAYDIZ0kJIIb7jhBnnve98rVVVVsuuuu8rixYvliCOOkFNPPVVKSkrki1/8otx2221y6aWXunOrVq2SY445Rt7+9rfHxZeKCA8++GC599575eabb5b29nY56aSTXI9w3rx5snHjRkd2559/vqxdu9bhwgsvdNch4tWrV8fFZzAYDAZDukhKhBdccIHr7b3vfe9zva899tjDkR7kuNdee7le4i9+8QvXG8SFCgE+8MADzl35xje+MS6+VER45ZVXyrXXXisHHXSQc4+Sbnl5uYtPe5kLFy505+kZkiaEedppp0l1dbXMmTMnLk6DwWAwGNJBUiLMBZIRocFgMBgMs4UdfLLKJYwIDQaDwZBv2MEnq1zCiNBgMBgM+YYdfLLKJYwIDQaDwZBv2MEnq1zCiNBgMBgM+YYdlixZIjOF0tJSN8vTYDAYDIZ8wQ5iYmJiYmJSxGJEaGJiYmJS1GJEaGJiYmJS1GJEaGJiYmJS1GJEaGJiYmJS1GJEaGJiYmJS1GJEaGJiYmJS1GJEaGJiYmJS1GJEaGJiYmJS1GJEaGKSZXnttdf8UyYmJnksRoQmJjkQyDAMExOT/BUjQhOTLAvE9//+3/9z2LJli/ztb38zMjQxyWMxIjQxybIoEXZ3d0tfX5/84x//kL///e9GhiYmeSpGhCYmWRAlOY4QHwRYWVnpCJD/RoImJvkrRoQmJlkQ7QVCfK+88oqsXLly/L+RoIlJfosRoYlJFkR7ghxfeOEFGRoackSYDyRok3ZMTFKLEaFJ0cpk5DAVAlEiZFxwzZo1rif46quvunOTSbppZCphkjYxMYkXI0KTopTJSE4nuCiShUM0HojviSeecK5RJZ7J0kH0uqajM06JIxxPpqKkrHFOJ65kkos4TUxmSowITSIv6ZBNWJRsWNqQ6h5Ig3ADAwMT4tffPtn19PTIsmXLJkyQSTdveg8kWltbKxUVFdLV1TVOXtq7nCyeRMJ9L774ouupJnPXpptPX6byjCYm+SpGhCaRF12nl44hJgzEUF9fL3fccUfSezgPafT398uJJ57oCErv9Y2/YsOGDY7E1q9f74iMmaPay0uVP84TjvToUe62227yhS98QfbYYw859NBD5dFHH3VENtXeoYaFRK+99toJRKhx6G/iDt+XjoTvnWreTEzySYwITSIpGFwIcOPGjXLMMcfISy+9NKUxuQULFsh1112XNLwSyPnnny+77767LF++fNxNCplAjE1NTRPcmfQGzzzzTNlpp53kox/9qHz5y1+Wo446yuWRdJKRohIJ6bW1tTkSXbJkiVx22WXys5/9TPbcc0/3m2dMl3CUoEjzgQcekD/84Q/jaei9GoZngWhJd3h4eLx3msolzHktCyYG1dTUuLg5Z2ISNTEiNImcKBFgdH/961/Ljjvu6Ho8YSOfTPTes846S2677baE4TXM6tWr5Ve/+pV897vflSOPPNKRhY7frVixwpGGhh0ZGXGE19raKn/961/l5JNPlr333lu+9rWvOUC6YSLz01MSVxJS0hocHJSbb75Z9tlnH0feie73RcsGgiNPp59+umzbts2dC7tYw2QG0b797W+XD3zgA3LJJZfI6OjoeF4SCee5j3hvv/1292z8NyI0iaIYEZpEUpQ0IMBPfvKTzo1YVVWV1HCHBUI744wznMtRCSEsSmznnnuuc3Puv//+jnBxW5ImY3esE1SiIDznGhoaxs9BOPQYIZgf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aaeN/z/kkENkjz32kLq6OjnrrLNcnCjNEUccEXfvkUceKfvtt59T5t133z3ueqEhFwaL3gAk66c128BY0Ej67//+75Qv/rp16+Q///M/ZeXKlXHXlixZEtd4mgz/+q//Kn/+85/ld7/7nXz3u9+V+vr6uDBhPP744y6v/vkw/vSnP8kf/vCHuPP5AoiKNa3ZlnxtYKET73nPe+TOO++UzZs3u4Z3okbPzTff7Oou0flddtklpV4+//zz479vv/12ecc73uF0ddGiRfL1r3/dEeQxxxwjZWVlE+771Kc+lVRXCPvII4+4e+kETKab+QDWIKYjaREhxipfp7FDWGeccYZr3Xz84x93PTmUB+P0+9//3lXs6tWrXcUdfPDB8rWvfc31IH/zm9/IgQceKJ/+9KcnECGtnje96U1OYb70pS/Jhz/8YVf5F110kTzxxBOux7jbbrvJTTfdFEeEfrr0ElBy8kc69957r4v7wgsvlF133dX1AM477zz50Y9+JF/+8pdd2u985ztdPonXf9bZBgaLNX3ZFMZu/HTyAbfccos89NBD8u53v9vVK42hb3/72/Ktb31L9t57b7nvvvtcHZ5wwgny7//+73LPPfe4ukNXPvShD7lW+Pe+9z05/PDD5eijj5bPfvazss8++7j7N2zY4Oqb3txxxx03IV2I8K677nK6STroJj3On//853Luuee6+37961+7Fj5E+773vc/1KIjnpz/9qfzgBz+QF154wenuiSee6Hodn/zkJ52O3XrrrXLQQQe5/PA8/jPPJnBlZdPjgM3K15nH9Pg/8pGPxJ3HhlHHXHvuueecPfvJT34iixcvdp4CeoF4GZQIIdSwPmCfvvrVrzq9+/GPfzweL4SJbqKTkB86hhcBbxbpYBNp/KPD2K1DDz1UvvKVr7i8YLvweKl3A12ix/i6173OESZ5ppeI/ZusQTZbSKeRlRYR5qtLFCxcuNCRHAbjjW98o9x///2OxGhxYXAwQBATBoNKx7BBYhgvXKF+j5BWzgc/+EHXG7jmmmvkP/7jP1wYXGAYHpQBI4Mx8YnQTxfXGkoLiZK3Rx991MUHMWPYcOeiWCjyxz72MafI3I+7IlEPIx/AAulsSb622AFERCPlM5/5jKt/9AJvAeRFyxpjhgHRHiF1/973vleefPJJ+dznPud054ADDnBESDwQILpJGIgL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3/9eli1bFhduqsBQffnLX5aSkhJ5/PHH5X3ve59T1mOPPVZ+85vfSFVV1XjYnXbaSS6//PIJ9z/66KMuP/vuu68cf/zxE8LPJHJhsOgNQLJ+WlHAt771LTn88MNl06ZNrqFyww03xIXJBOjFr3/967jzYfzv//6v/PGPf5Qf/OAHcuqpp8ZdjxIgKta0ZlvypYG1cOFC917/9a9/dQR47bXXut9+uOniuuuuk0996lPORvjXFPfee6/rWPjnpwIafzTC/PP5CNYgpiNpESHGaiansVdUVMhHPvIRueiiiyacX7lypSMUjMRJJ53keosQF8bo4x//uDzyyCPyjne8w5HNNddcI2vXrnXk8cQTT8i8efPkm9/8piMleliQLD2www47bDx+wv74xz+Oy89tt90m55xzjnziE59wZEkvjpb4N77xDff7d7/7ney5554uPfLHNVp93EvPdtddd3Xpa3y8FGeeeaYLt9tuu00gW58IIcB3v/vd7uX5/Oc/74wv+f7KV74iP/rRj6at1FMBBos1fdkUxm78dKIAen///M//LIsXL55w/p577nF1hD6gN1//+tdlv/32c+FvvfVWp5d4G372s585nbjvvvtc/R5xxBGy8847u94+RPjVr37V3Ucdc47GE/r95je/2emLT4RHHXWU8yT470xUgCsrmx4HbFa+zDzGxmB7/PPYJeoOG3DTTTfJL3/5S/na174m3/nOd1x4vAK85xs2bHD/uU6HAP34v//7P2er8DQRF3bmrLPOct6lD33oQ1JZWSl//vOfnW7RG33jG9/oGmu77767000a12Fbii69613vkqOPPtrdgx69//3vd/qNnT3ttNOczuHNQk9/8YtfyIMPPuj0Gx284oorZP78+fLhD39YDj74YPnCF76QFW9aNpBOIystIpxJlyig1QTBhQmBiqaSv/jFLzrjQSVhWD75yU+6in7DG97gCh5Fwv3A/fToqCAIlMpEwVAmWmPvfOc75cYbb3StNU0Do0MF8xuj86tf/cqlyW8UA5IivgMOOMCRL3GiCOTnve99ryMllJA0MEzEU1NTI7vssotTJE3nox/9qFMajB8kGn72MBHiqgUf+9jHnFJjPE844QT33CgrhhL45ZdLsEA6W5IvLfZM8Nxzz8nrXvc6Wbp06fg5dFR77RgX6gsDgxsTA4ZxQCdXrVrliO23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";
	private static final String FREESCREENSHOTSERVICE = "https://image.thum.io/get/width/450/crop/700/noanimate/";
	private static final String[] SITESWITHDEFAULTIMAGE= {"catalog-website"};
	@Cacheable(value = "base64Images")
	public String getScreenshot(String siteUrl) {
		String toReturn = DEFAULTBASE64IMAGE;
		if(Arrays.asList(SITESWITHDEFAULTIMAGE).stream().anyMatch(siteUrl::contains))
			return toReturn;
		for (int i = 0; i < RETRYCOUNT; i++) {
			try {
				URL url = new URL(FREESCREENSHOTSERVICE + siteUrl);
				BufferedImage image = ImageIO.read(url);
				toReturn = encodeToString(image, "png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	private String encodeToString(BufferedImage image, String type) throws IOException {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, type, bos);
		byte[] imageBytes = bos.toByteArray();
		imageString = Base64.getEncoder().encodeToString(imageBytes);
		bos.close();
		return imageString;
	}

}
