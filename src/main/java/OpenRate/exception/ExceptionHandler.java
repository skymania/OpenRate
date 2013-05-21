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

package OpenRate.exception;

import java.util.ArrayList;

/**
 * The handler class is designed for critical failures during plug in
 * processing. Since plug ins are executed within one or more threads, they
 * cannot propagate errors up the call stack to the main thread. To remedy
 * this problem, the ExceptionHandler is made available to Filters to report
 * critical FATAL processing errors. It is shared by all the threads
 * (including the main thread running the pipeline) and is checked at the end
 * of each cycle so that application shutdown can happen cleanly as soon as
 * possible. This is a common pattern for error reporting in a multi-
 * threaded environment.
 */
public class ExceptionHandler
{
  /**
   * CVS version info - Automatically captured and written to the Framework
   * Version Audit log at Framework startup. For more information
   * please <a target='new' href='http://www.open-rate.com/wiki/index.php?title=Framework_Version_Map'>click here</a> to go to wiki page.
   */
  public static String CVS_MODULE_INFO = "OpenRate, $RCSfile: ExceptionHandler.java,v $, $Revision: 1.18 $, $Date: 2013-05-13 18:12:11 $";

  /**
   * list of reported exceptions. Normally the pipeline will
   * shutdown as soon as a fatal error occurs, but the timing
   * may allow for multiple exceptions to be reported before
   * the pipeline is notified and/or can react. Therefore a list
   * is maintained to store any subsequent errors reporting
   * during the interim.
   */
  private ArrayList<Exception> exceptionQueue = new ArrayList<>();

  // lock for thread safety.
  private final Object  lock     = new Object();
  private boolean hasError = false;

  /**
   * Default constructor
   */
  public ExceptionHandler()
  {
    super();
  }

 /**
  * Clear down reported exceptions
  */
  public void clearExceptions()
  {
    synchronized (lock)
    {
      exceptionQueue.clear();
      hasError = false;
    }
  }

  /**
   * Method used by modules to report a fatal error.
   *
   * @param exception The processing exception to report
   */
  public void reportException(ProcessingException exception)
  {
    synchronized (lock)
    {
      exceptionQueue.add(exception);
      hasError = true;
    }
  }

  /**
   * Method used by modules to report a fatal error.
   *
   * @param exception The processing exception to report
   */
  public void reportException(InitializationException exception)
  {
    synchronized (lock)
    {
      exceptionQueue.add(exception);
      hasError = true;
    }
  }

  /**
   * Rethrow the first reported exception.
   *
   * @throws ProcessingException
   */
  public void rethrowException() throws ProcessingException
  {
    synchronized (lock)
    {
      // locks are re-entrant, it's ok that hasError is
      // synchronized on 'lock' also.
      if (hasError())
      {
        // get first exception
        throw (ProcessingException) exceptionQueue.get(0);
      }
    }
  }

  /**
   * Rethrow the first reported exception.
   *
   * @return The exception list
   */
  public ArrayList<Exception> getExceptionList()
  {
    synchronized (lock)
    {
      // locks are re-entrant, it's ok that hasError is
      // synchronized on 'lock' also.
      // We make a copy to avoid concurrent modification exception
      ArrayList<Exception> queueCopy = new ArrayList<>();
      queueCopy.addAll(exceptionQueue);
      return queueCopy;
    }
  }

  /**
   * True/False flag allowing the pipeline components to recognize
   * when a fatal error has been reported.
   *
   * @return true if a fatal error has been reported
   */
  public boolean hasError()
  {
    synchronized (lock)
    {
      return this.hasError;
    }
  }
}