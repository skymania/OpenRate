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
import OpenRate.resource.IResource;

/**
 * AbstractLogFactory - create Factory for a certain logging library.
 */
public abstract class AbstractLogFactory implements IResource
{
  /**
   * CVS version info - Automatically captured and written to the Framework
   * Version Audit log at Framework startup. For more information
   * please <a target='new' href='http://www.open-rate.com/wiki/index.php?title=Framework_Version_Map'>click here</a> to go to wiki page.
   */
  public static String CVS_MODULE_INFO = "OpenRate, $RCSfile: AbstractLogFactory.java,v $, $Revision: 1.17 $, $Date: 2013-05-13 18:12:12 $";

  /**
   * This is the key name we will use for referencing this object from the
   * Resource context
   */
  public static final String RESOURCE_KEY = "LogFactory";

  // reference to the exception handler
  protected ExceptionHandler handler;

  /**
   * Get default logger. This method exists to support backward
   * compatibility prior to the factory class. Prefer
   * getLogger(String type) instead.
   *
   * @return The default logger
   * @throws ConfigurationException
   */
  public abstract AstractLogger getDefaultLogger() throws InitializationException;

  /**
   * Get a logger instance for the provided type.
   *
   * @param type The logger type
   * @return The logger
   * @throws ConfigurationException
   */
  public abstract AstractLogger getLogger(String type);

  /**
   * Get a logger instance for the provided type.
   *
   * @param type The logger type
   * @return The logger
   * @throws ConfigurationException
   */
  public AstractLogger getLogger(Class<?> type) throws InitializationException
  {
    return getLogger(type.getName());
  }

  /**
   * Get a AbstractLogFactory based on the implementation class name
   * provided as a parameter.
   *
   * @param factoryImpl
   * @return The log factory
   * @throws ConfigurationException
   */
  public static AbstractLogFactory getFactory(String factoryImpl)
    throws InitializationException
  {
    AbstractLogFactory factory = null;

    try
    {
      if (factoryImpl == null)
      {
        throw new InitializationException("LogFactory className == null");
      }

      Class<?> type = Class.forName(factoryImpl);
      Object   obj  = type.newInstance();
      factory     = (AbstractLogFactory) obj;
    }
    catch (ClassCastException cce)
    {
      throw new InitializationException("LogFactory.getFactory(): " +
        "LogFactory class name is not a LogFactory sub-class.", cce);
    }
    catch (ClassNotFoundException cnfe)
    {
      throw new InitializationException("LogFactory.getFactory(): " +
        "LogFactory implementation class not found in classpath.", cnfe);
    }
    catch (InstantiationException ie)
    {
      throw new InitializationException("LogFactory.getFactory(): " +
        "No default constructor for LogFactory", ie);
    }
    catch (IllegalAccessException iae)
    {
      throw new InitializationException("LogFactory.getFactory(): " +
        "Cannot invoke default constructor on LogFactory. " +
        "Check that it's visibility is public.", iae);
    }

    return factory;
  }

  /**
   * Set the exception handler for handling any exceptions.
   *
   * @param handler the handler to set
   */
  @Override
  public void setHandler(ExceptionHandler handler)
  {
    this.handler = handler;
  }
}