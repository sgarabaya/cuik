import { VNode } from 'preact';
import { useState, useEffect } from 'preact/hooks';

interface LoaderProps<T> {
    fn: () => Promise<T>;
    children: (data: T | null) => VNode;
}

export function Loader<T>({ fn, children }: LoaderProps<T>) {
    const [data, setData] = useState<T | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fn().then(res => {
            setData(res);
            setLoading(false);
        });
    }, [fn]);

    return (
        <div class="flex justify-center align-center">
            <div style={{
                opacity: loading ? 0.5 : 1,
                pointerEvents: loading ? 'none' : 'auto',
                transition: 'opacity 0.2s'
            }}>
                {children(data)}
            </div>

            {loading && (
                <div class="loader" />
            )}
        </div>
    );
}