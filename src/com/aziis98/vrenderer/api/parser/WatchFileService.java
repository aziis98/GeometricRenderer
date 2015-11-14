package com.aziis98.vrenderer.api.parser;

import java.nio.file.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class WatchFileService extends Thread {

    private final Path path;
    private final FileChangedListener action;
    private AtomicBoolean stop = new AtomicBoolean( false );

    public WatchFileService(Path path, FileChangedListener fileChangedListener) {
        this.path = path;
        this.action = fileChangedListener;
    }

    public WatchFileService(Path path, FileChangedAction fileChangedAction) {
        this( path, ignored -> fileChangedAction.onChange() );
    }

    public boolean isStopped() { return stop.get(); }

    public void stopThread() { stop.set( true ); }

    @Override
    public void run() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService())
        {
            Path parentPath = this.path.getParent();
            parentPath.register( watcher, StandardWatchEventKinds.ENTRY_MODIFY );
            while ( !isStopped() )
            {
                WatchKey key;
                try
                {
                    key = watcher.poll( 25, TimeUnit.MILLISECONDS );
                }
                catch (InterruptedException e)
                {
                    return;
                }
                if ( key == null )
                {
                    Thread.yield();
                    continue;
                }

                for (WatchEvent<?> event : key.pollEvents())
                {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if ( kind == StandardWatchEventKinds.OVERFLOW )
                    {
                        Thread.yield();
                        continue;
                    }
                    else if ( kind == java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
                            && filename.toString().equals( path.getFileName().toString() ) )
                    {
                        action.onChange( path );
                    }
                    boolean valid = key.reset();
                    if ( !valid )
                    {
                        break;
                    }
                }
                Thread.yield();
            }
        }
        catch (Throwable e)
        {
            // Log or rethrow the error
        }
    }

    @FunctionalInterface
    public interface FileChangedListener {
        default void call(Path path) {
            try
            {
                onChange(path);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        void onChange(Path path) throws Exception;
    }

    @FunctionalInterface
    public interface FileChangedAction {
        default void call() {
            try
            {
                onChange();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        void onChange() throws Exception;
    }

}
