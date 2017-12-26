package redis.clients.jedis;

/**
 * Created by zhiyinghou on 2017/12/26.
 */
public class Path {

  private final String strPath;

  public Path(final String strPath) {
    this.strPath = strPath;
  }

  /**
   * Makes a root path
   *
   * @return the root path
   */
  public static Path RootPath() {
    return new Path(".");
  }

  @Override
  public String toString() {
    return strPath;
  }

}
