package micro.commons.component;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.Getter;

/**
 * 二维码组件
 * 
 * @author gewx
 **/
@Getter
public final class QrCodeComponent {

	/**
	 * 二维码格式化图片类型
	 **/
	enum ImageFormat {
		JPG;
	}

	private static final String CHARSET = "utf-8";

	private final int size;

	private final int width;

	private final int height;

	private QrCodeComponent(Builder builder) {
		this.size = builder.size;
		this.width = builder.width;
		this.height = builder.height;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private int size;

		private int width;

		private int height;

		public Builder() {
			this.size = 300;
			this.width = 60;
			this.height = 60;
		}

		public Builder size(int size) {
			this.size = size;
			return this;
		}

		public Builder width(int width) {
			this.width = width;
			return this;
		}

		public Builder height(int height) {
			this.height = height;
			return this;
		}

		public QrCodeComponent build() {
			return new QrCodeComponent(this);
		}
	}

	/**
	 * 二维码编码并返回字节数组
	 * 
	 * @author gewx
	 * @param content 内容
	 * @param format  格式化类型
	 * @return 二维码字节数组
	 **/
	public byte[] encode(String content, ImageFormat format) throws IOException, WriterException {
		return encode(content, false, format);
	}

	/**
	 * 二维码编码并返回字节数组
	 * 
	 * @author gewx
	 * @param content  内容
	 * @param compress 是否压缩
	 * @param format   格式化类型
	 * @return 二维码字节数组
	 **/
	public byte[] encode(String content, boolean compress, ImageFormat format) throws IOException, WriterException {
		return encode(content, null, compress, format);
	}

	/**
	 * 二维码编码并返回字节数组
	 * 
	 * @author gewx
	 * @param content  内容
	 * @param logo     插入的Logo图片
	 * @param compress 是否压缩
	 * @param format   格式化类型
	 * @return 二维码字节数组
	 **/
	public byte[] encode(String content, File logo, boolean compress, ImageFormat format)
			throws IOException, WriterException {
		BufferedImage image = createImage(content, logo, compress);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, format.name(), outputStream);
		return outputStream.toByteArray();
	}

	/**
	 * 二维码编码并输出到磁盘
	 * 
	 * @author gewx
	 * @param content 内容
	 * @param dest    二维码输出地址
	 * @param format  格式化类型
	 * @return void
	 **/
	public void encode(String content, File dest, ImageFormat format) throws IOException, WriterException {
		encode(content, false, dest, format);
	}

	/**
	 * 二维码编码并输出到磁盘
	 * 
	 * @author gewx
	 * @param content  内容
	 * @param compress 是否压缩
	 * @param dest     二维码输出地址
	 * @param format   格式化类型
	 * @return void
	 **/
	public void encode(String content, boolean compress, File dest, ImageFormat format)
			throws IOException, WriterException {
		encode(content, null, compress, dest, format);
	}

	/**
	 * 二维码编码并输出到磁盘
	 * 
	 * @author gewx
	 * @param content  内容
	 * @param logo     插入的Logo图片
	 * @param compress 是否压缩
	 * @param dest     二维码输出地址
	 * @param format   格式化类型
	 * @return void
	 **/
	public void encode(String content, File logo, boolean compress, File dest, ImageFormat format)
			throws IOException, WriterException {
		BufferedImage image = createImage(content, logo, compress);
		ImageIO.write(image, format.name(), dest);
	}

	/**
	 * 二维码解码返回数据
	 * 
	 * @author gewx
	 * @param file 二维码
	 * @return 解码数据
	 **/
	public String decode(File file) throws IOException, NotFoundException {
		BufferedImage image = ImageIO.read(file);
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		return result.getText();
	}

	/**
	 * 创建二维码
	 **/
	private BufferedImage createImage(String content, File logo, boolean compress) throws IOException, WriterException {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, this.size, this.size,
				hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}

		if (logo == null) {
			return image;
		}

		insertLogo(image, logo, compress);
		return image;
	}

	/**
	 * 创建二维码
	 **/
	private void insertLogo(BufferedImage source, File logo, boolean compress) throws IOException {
		Image logoImage = ImageIO.read(logo);
		int width = logoImage.getWidth(null);
		int height = logoImage.getHeight(null);
		if (compress) {
			if (width > this.width) {
				width = this.width;
			}
			if (height > this.height) {
				height = this.height;
			}
			Image image = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			logoImage = image;
		}
		Graphics2D graph = source.createGraphics();
		int x = (this.size - width) / 2;
		int y = (this.size - height) / 2;
		graph.drawImage(logoImage, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
}
