/* ====================================================================
 * Limited Evaluation License:
 *
 * The exclusive owner of this work is the OpenRate project.
 * This work, including all associated documents and components
 * is Copyright of the OpenRate project 2006-2013.
 *
 * The following restrictions apply unless they are expressly relaxed in a
 * contractual agreement between the license holder or one of its officially
 * assigned agents and you or your organisation:
 *
 * 1) This work may not be disclosed, either in full or in part, in any form
 *    electronic or physical, to any third party. This includes both in the
 *    form of source code and compiled modules.
 * 2) This work contains trade secrets in the form of architecture, algorithms
 *    methods and technologies. These trade secrets may not be disclosed to
 *    third parties in any form, either directly or in summary or paraphrased
 *    form, nor may these trade secrets be used to construct products of a
 *    similar or competing nature either by you or third parties.
 * 3) This work may not be included in full or in part in any application.
 * 4) You may not remove or alter any proprietary legends or notices contained
 *    in or on this work.
 * 5) This software may not be reverse-engineered or otherwise decompiled, if
 *    you received this work in a compiled form.
 * 6) This work is licensed, not sold. Possession of this software does not
 *    imply or grant any right to you.
 * 7) You agree to disclose any changes to this work to the copyright holder
 *    and that the copyright holder may include any such changes at its own
 *    discretion into the work
 * 8) You agree not to derive other works from the trade secrets in this work,
 *    and that any such derivation may make you liable to pay damages to the
 *    copyright holder
 * 9) You agree to use this software exclusively for evaluation purposes, and
 *    that you shall not use this software to derive commercial profit or
 *    support your business or personal activities.
 *
 * This software is provided "as is" and any expressed or impled warranties,
 * including, but not limited to, the impled warranties of merchantability
 * and fitness for a particular purpose are disclaimed. In no event shall
 * Tiger Shore Management or its officially assigned agents be liable to any
 * direct, indirect, incidental, special, exemplary, or consequential damages
 * (including but not limited to, procurement of substitute goods or services;
 * Loss of use, data, or profits; or any business interruption) however caused
 * and on theory of liability, whether in contract, strict liability, or tort
 * (including negligence or otherwise) arising in any way out of the use of
 * this software, even if advised of the possibility of such damage.
 * This software contains portions by The Apache Software Foundation, Robert
 * Half International.
 * ====================================================================
 */

package OpenRate.logging;

import OpenRate.exception.ExceptionHandler;
import OpenRate.exception.InitializationException;
import OpenRate.resource.ResourceContext;

/**
 * Helper method to creating AstractLogger instance.
 *
 */
public class LogUtil
{
  /**
   * CVS version info - Automatically captured and written to the Framework
   * Version Audit log at Framework startup. For more information
   * please <a target='new' href='http://www.open-rate.com/wiki/index.php?title=Framework_Version_Map'>click here</a> to go to wiki page.
   */
  public static String CVS_MODULE_INFO = "OpenRate, $RCSfile: LogUtil.java,v $, $Revision: 1.23 $, $Date: 2013-05-13 18:12:12 $";

  // Get the utilities for handling the XML configuration
  private static LogUtil logUtilsObj;

  // Exception handler
  private static ExceptionHandler handler;

 /**
  * This utility function returns the singleton instance of LogUtils
  *
  * @return    the instance of PropertyUtils
  */
  public static LogUtil getLogUtil()
  {
    if(logUtilsObj == null)
    {
      logUtilsObj = new LogUtil();
    }

    return logUtilsObj;
  }

  /**
   * Get the appropriate AstractLogger instance for this category
   *
   * @param LoggerName The logger name to get
   * @return The logger
   * @throws ConfigurationException
   */
  public ILogger getLogger(String LoggerName)
  {
    AstractLogger          logger = null;

    ResourceContext ctx    = new ResourceContext();

    // try the new Logging model.
    AbstractLogFactory factory = (AbstractLogFactory) ctx.get(AbstractLogFactory.RESOURCE_KEY);

    if (factory == null)
    {
      // no factory registered, error
      handler.reportException(new InitializationException("No log factory found"));
    }
    else
    {
      logger = factory.getLogger(LoggerName);
    }

    if (logger == null)
    {
      handler.reportException(new InitializationException("unable to load logger. Resource not found"));
    }

    return logger;
  }

  /**
   * Get the appropriate AstractLogger instance for this category
   *
   * @param LoggerName The logger name to get
   * @return The logger
   * @throws ConfigurationException
   */
  public static AstractLogger getStaticLogger(String LoggerName)
  {
    AstractLogger          logger = null;

    ResourceContext ctx    = new ResourceContext();

    // try the new Logging model.
    AbstractLogFactory factory = (AbstractLogFactory) ctx.get(AbstractLogFactory.RESOURCE_KEY);

    if (factory == null)
    {
      // no factory registered, error
      handler.reportException(new InitializationException("No log factory found"));
    }
    else
    {
      logger = factory.getLogger(LoggerName);
    }

    if (logger == null)
    {
      handler.reportException(new InitializationException("unable to load logger. Resource not found"));
    }

    return logger;
  }

 /**
  * Set the logger for the log factory itself. This is not possible at creation
  * time because the log is not yet initialised
  *
  * @param FWLog
  */
  public void setLoggerLog(ILogger FWLog)
  {
    ResourceContext ctx    = new ResourceContext();

    // try the new Logging model.
    LogFactory factory = (LogFactory) ctx.get(AbstractLogFactory.RESOURCE_KEY);

    factory.setFrameworkLog(FWLog);
  }

  /**
   * This method allows failures to be logged, even if the failure
   * is in the logging package.
   *
   * @return The default logger
   */
  public AstractLogger getDefaultLogger()
  {
    return new DefaultLogger();
  }

 /**
  * Prepare a string for logging ECI commands from a pipe module
  *
  * @param SymbolicName The name of the module
  * @param PipeName The name of the pipe the module belongs to
  * @param Command The command that was requested
  * @param Parameter The parameter for the command
  * @return The compiled log string
  */
  public static String LogECIPipeCommand(String SymbolicName, String PipeName, String Command, String Parameter)
  {
    return "Command <" + Command + "> handled by <" + SymbolicName + "> in pipe <" + PipeName + "> with parameter <" + Parameter + ">";
  }

 /**
  * Prepare a string for logging ECI commands from a framework module
  *
  * @param Command The command that was requested
  * @param Parameter The parameter for the command
  * @return The compiled log string
  */
  public static String LogECIFWCommand(String Command, String Parameter)
  {
    return "Command <" + Command + "> handled by <Framework> with parameter <" + Parameter + ">";
  }

 /**
  * Prepare a string for logging ECI commands from a cache module
  *
  * @param SymbolicName The name of the module
  * @param Command The command that was requested
  * @param Parameter The parameter for the command
  * @return The compiled log string
  */
  public static String LogECICacheCommand(String SymbolicName, String Command, String Parameter)
  {
    return "Command <" + Command + "> handled by <" + SymbolicName + "> with parameter <" + Parameter + ">";
  }

  /**
   * @return the handler
   */
  public ExceptionHandler getHandler() {
    return handler;
  }

  /**
   * @param handler the handler to set
   */
  public void setHandler(ExceptionHandler handler) {
    this.handler = handler;
  }
}
