/**
 * The main API for sending time-based object notifications.
 *
 * The classes defined represent object-senders and receivers which operate based on the current time using the
 * {@link java.time.LocalDateTime} class.
 *
 * <header>
 *     <p>{@link model.notifications.Notifier} collects broadcast content.</p>
 * </header>
 *
 * <header>
 *     <p>{@link model.notifications.ChronologicalBroadcaster} broadcasts content chronologically based on
 *     current time.</p>
 *     <p>{@link model.notifications.IChronological} represents a chronologically based object.</p>
 *     <p>{@link model.notifications.IObjectBroadcaster} broadcasts objects to listeners.</p>
 *     <p>{@link model.notifications.IObjectBroadcastListener} receives objects from broadcasts.</p>
 * </header>
 */
package model.notifications;

